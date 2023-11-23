package xyz.blushyes.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import xyz.blushyes.file.AliyunOSS;

@Configuration
@EnableConfigurationProperties({QuickFIleProperties.class, AliyunOSS.class})
public class QuickFIleAutoConfig {
}