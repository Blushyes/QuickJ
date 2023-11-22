package xyz.blushyes

interface Generator {
    fun execute() {
        execute("xyz.blushyes")
    }

    fun execute(basePackage: String)
}