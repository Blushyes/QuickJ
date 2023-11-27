package xyz.blushyes

class ConvertUtils {
    fun toCamelCase(input: String): String {
        return input.split('_')
            .joinToString("") { it.replaceFirstChar { char -> char.uppercase() } }
    }

    fun toCamelCaseWithFirstLowerLetter(input: String): String {
        return input.split("_")
            .joinToString(separator = "") {
                // 对于首个单词，保持小写；对于其他单词，首字母大写
                if (it.isEmpty()) "" else it.replaceFirstChar { char -> char.uppercase() }
            }
    }
}