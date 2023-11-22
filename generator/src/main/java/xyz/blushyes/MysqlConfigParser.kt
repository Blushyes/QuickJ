package xyz.blushyes

interface MysqlConfigParser {
    fun parse(): MysqlConnectionConfig;
}