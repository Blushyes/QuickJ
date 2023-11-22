package xyz.blushyes

import org.apache.maven.settings.io.xpp3.SettingsXpp3Reader
import java.io.File
import java.io.FileInputStream

class MavenPropertiesMysqlConfigParser : MysqlConfigParser {
    override fun parse(): MysqlConnectionConfig {
        val settingXmlPath = System.getProperty("user.dir") + File.separator + "settings.xml"
        val settings = SettingsXpp3Reader().read(FileInputStream(File(settingXmlPath)))
        return settings.profiles.firstOrNull { it.id == "dev" }?.let {
            val props = it.properties
            val address = props["mysql.address"].toString()
            val username = props["mysql.username"].toString()
            val password = props["mysql.password"].toString()
            val db = props["mysql.db.name"].toString()
            val splitAddress = address.split(":")
            MysqlConnectionConfig(splitAddress[0], splitAddress[1].toInt(), db, username, password)
        } ?: throw RuntimeException()
    }
}