package xyz.blushyes

fun String.toCamelCase(): String {
    return this.split('_')
        .joinToString("") { it.replaceFirstChar { char -> char.uppercase() } }
}

fun String.toCamelCaseWithFirstLowerLetter(): String {
    return split("_")
        .mapIndexed { index, s ->
            if (index == 0) s
            else s.replaceFirstChar { it.uppercaseChar() }
        }
        .joinToString("")
}