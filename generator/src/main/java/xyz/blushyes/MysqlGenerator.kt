package xyz.blushyes

import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.runtime.RuntimeConstants
import xyz.blushyes.common.BasePO
import xyz.blushyes.common.LogicPO
import xyz.blushyes.common.TimePO
import java.io.StringWriter
import java.sql.DriverManager

class MysqlGenerator(
    private val host: String,
    private val port: Number,
    private val db: String,
    private val username: String,
    private val password: String
) : Generator {

    private val engine = VelocityEngine().apply {
        setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath")
        setProperty(
            "classpath.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader"
        )
        init()
    }

    private val typeMapping = mapOf(
        "INT" to "Integer",
        "BIGINT" to "Long",
        "TINYINT" to "boolean",
        "SMALLINT" to "Short",
        "FLOAT" to "Float",
        "DOUBLE" to "Double",
        "DECIMAL" to "BigDecimal",
        "CHAR" to "String",
        "VARCHAR" to "String",
        "TEXT" to "String",
        "BLOB" to "byte[]",
        "DATE" to "Date",
        "TIME" to "Time",
        "BOOLEAN" to "boolean"
    )

    private val logicPoFields = LogicPO::class.java.declaredFields.map { toSnakeCase(it.name) }.toSet()
    private val timePoFields = TimePO::class.java.declaredFields.map { toSnakeCase(it.name) }.toSet()
    private val basePoFields = BasePO::class.java.declaredFields.map { toSnakeCase(it.name) }.toSet()


    override fun execute(basePackage: String) {
        val url = "jdbc:mysql://$host:$port/$db?useUnicode=true&characterEncoding=utf-8&serverTimezone=Asia/Shanghai"
        getTableSchemas(url, username, password, db).forEach {
            val context = VelocityContext().apply {
                put("ConvertUtils", ConvertUtils())
                put("tableName", it.name)
                put("tableComment", it.comment)
                put("columns", it.columns)
                put("poPackage", "xyz.blushyes.po")
                val resetColumnByFields = fun(fields: Set<String>) {
                    put("columns", it.columns.filter { col -> !fields.contains(toSnakeCase(col.name)) })
                }
                val extendWith = fun(fields: Set<String>): Boolean {
                    return it.columns.map { col -> col.name }.intersect(fields).isNotEmpty()
                }
                if (extendWith(basePoFields)) {
                    put("hasExtend", true)
                    put("extendClass", BasePO::class.java.name)
                    put("extendClassName", BasePO::class.java.simpleName)
                    resetColumnByFields(basePoFields + timePoFields + logicPoFields)
                } else if (extendWith(timePoFields)) {
                    put("hasExtend", true)
                    put("extendClass", TimePO::class.java.name)
                    put("extendClassName", TimePO::class.java.simpleName)
                    resetColumnByFields(timePoFields + logicPoFields)
                } else if (extendWith(logicPoFields)) {
                    put("hasExtend", true)
                    put("extendClass", LogicPO::class.java.name)
                    put("extendClassName", LogicPO::class.java.simpleName)
                    resetColumnByFields(logicPoFields)
                }
            }
            val poTemplate = engine.getTemplate("templates/po.vm", "utf-8")
            val writer = StringWriter()
            poTemplate.merge(context, writer)
            println(writer)
            write("$basePackage.po", toCamelCase(it.name) + ".java", writer.toString())
        }
    }


    private fun getTableSchemas(dbUrl: String, user: String, password: String, dbName: String): List<TableInfo> {
        val tableInfos = mutableListOf<TableInfo>()

        // 需要改进这个SQL，以包含表的注释
        val tablesQuery = """
            SELECT TABLE_NAME, TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?
        """.trimIndent()

        val columnsQuery = """
            SELECT COLUMN_NAME, DATA_TYPE, COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?
        """.trimIndent()

        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            DriverManager.getConnection(dbUrl, user, password).use { conn ->
                conn.prepareStatement(tablesQuery).use { tablesStmt ->
                    tablesStmt.setString(1, dbName)

                    val tablesResultSet = tablesStmt.executeQuery()
                    while (tablesResultSet.next()) {
                        val tableName = tablesResultSet.getString("TABLE_NAME")
                        var tableComment = tablesResultSet.getString("TABLE_COMMENT")
                        if (tableComment == "" || tableComment == null) {
                            tableComment = tableName
                        }

                        val columns = mutableListOf<ColumnInfo>()

                        conn.prepareStatement(columnsQuery).use { columnsStmt ->
                            columnsStmt.setString(1, dbName)
                            columnsStmt.setString(2, tableName)

                            val columnsResultSet = columnsStmt.executeQuery()
                            while (columnsResultSet.next()) {
                                val columnName = columnsResultSet.getString("COLUMN_NAME")
                                val dataType =
                                    columnsResultSet.getString("DATA_TYPE").uppercase().let { typeMapping[it] ?: it }
                                val columnComment = columnsResultSet.getString("COLUMN_COMMENT")

                                columns += ColumnInfo(columnName, dataType, columnComment)
                            }
                        }

                        tableInfos += TableInfo(tableName, columns, tableComment)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return tableInfos
    }
}