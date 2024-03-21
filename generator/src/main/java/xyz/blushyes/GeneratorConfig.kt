package xyz.blushyes

/**
 * TODO 暂时先直接用变量配置
 */
val IGNORE_TABLE = listOf(
    "user",
    "permission",
    "role",
    "user_role_rel",
    "role_permission_rel",
    // TODO 改为动态获取flyway表名
    "flyway_schema_history"
)