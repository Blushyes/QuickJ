package xyz.blushyes

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.function.Consumer

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

fun getModuleJavaDirPath(module: String): String {
    return pathOf(listOf(getModuleSrcPath(module), "main", "java"))
}

fun getModuleResourcesDirPath(module: String): String {
    return pathOf(listOf(getModuleSrcPath(module), "main", "resources"))
}

fun getModuleSrcPath(module: String): String {
    return pathOf(listOf(System.getProperty("user.dir"), module, "src"))
}

fun pathOf(list: List<String?>): String {
    val builder = StringBuilder()
    list.forEach(Consumer { name -> builder.append(name).append(File.separator) })
    return builder.toString()
}

fun transferPath(module: String, packageName: String): String {
    return getModuleJavaDirPath(module) + File.separatorChar + packageName.replace('.', File.separatorChar)
}

fun join(path1: String, path2: String): String {
    return "${path1}${File.separatorChar}${path2}"
}

fun write(module: String, packageName: String, fullFileName: String, content: String) {
    val outputPath = transferPath(module, packageName)
    val directory = File(outputPath)

    if (!directory.exists() && !directory.mkdirs()) {
        throw RuntimeException("目录不存在，且创建目录失败")
    }

    Files.writeString(Paths.get(outputPath + File.separatorChar + fullFileName), content)
}

fun safeWrite(module: String, packageName: String, fullFileName: String, content: String) {
    val outputPath = transferPath(module, packageName)
    if (Files.exists(Paths.get(outputPath + File.separator + fullFileName))) {
        println("文件已存在")
        return
    }
    write(module, packageName, fullFileName, content)
}