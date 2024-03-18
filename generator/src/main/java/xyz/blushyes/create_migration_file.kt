package xyz.blushyes

import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


/**
 * 创建Flyway的迁移文件
 * TODO 是否需要支持U和R格式的迁移文件？
 */
fun main() {
    print("输入迁移文件描述：")
    val scanner = Scanner(System.`in`)
    val splitDescription = scanner.nextLine().split(" ")
    val description = splitDescription.joinToString("_").replaceFirstChar { it.uppercaseChar() }
    val version = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
    val fileName = "V${version}__${description}.sql"
    val file = File(pathOf(listOf(getModuleResourcesDirPath("core"), "db", "migration", fileName)))
    if (file.exists()) {
        println("文件已存在，请重新输入")
        return
    }
    file.createNewFile()
    println("$file created")
}