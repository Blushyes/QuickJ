package xyz.blushyes

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Consumer


val SRC_PATH = pathOf(listOf(System.getProperty("user.dir"), "pojo", "src", "main", "java"))

fun toCamelCase(input: String): String {
    return input.split('_')
        .joinToString("") { it.replaceFirstChar { char -> char.uppercase() } }
}

fun toSnakeCase(input: String): String {
    return input.mapIndexed { index, char ->
        when {
            char.isUpperCase() && index != 0 -> "_${char.lowercaseChar()}"
            else -> char.lowercaseChar().toString()
        }
    }.joinToString("")
}

fun pathOf(list: List<String?>): String {
    val builder = StringBuilder()
    list.forEach(Consumer { name -> builder.append(name).append(File.separator) })
    return builder.toString()
}

fun transferPath(packageName: String): String {
    return SRC_PATH + File.separatorChar + packageName.replace('.', File.separatorChar)
}

fun join(path1: String, path2: String): String {
    return "${path1}${File.separatorChar}${path2}"
}

fun write(packageName: String, fullFileName: String, content: String) {
    val outputPath = transferPath(packageName)
    val directory = File(outputPath)

    if (!directory.exists() && !directory.mkdirs()) {
        throw RuntimeException("目录不存在，且创建目录失败")
    }

    Files.writeString(Paths.get(outputPath + File.separatorChar + fullFileName), content)
}

fun softWrite(packageName: String, fullFileName: String, content: String) {
    val outputPath = transferPath(packageName)
    if (Files.exists(Paths.get(outputPath))) {
        println("文件已存在")
        return
    }
    write(packageName, fullFileName, content)
}