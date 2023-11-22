package xyz.blushyes

class ConvertUtils {
    fun toCamelCase(input: String): String {
        return input.split('_')
            .joinToString("") { it.replaceFirstChar { char -> char.uppercase() } }
    }
}