package xyz.blushyes

data class MysqlConnectionConfig(
    val host: String,
    val port: Number,
    val db: String,
    val username: String,
    val password: String
) {
}