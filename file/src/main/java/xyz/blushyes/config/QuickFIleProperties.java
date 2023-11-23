package xyz.blushyes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import xyz.blushyes.file.AliyunOSS;

@ConfigurationProperties("quick-file")
public class QuickFIleProperties {

    private String strategyImpl = AliyunOSS.class.getName();

    public String getStrategyImpl() {
        return strategyImpl;
    }

    public void setStrategyImpl(String strategyImpl) {
        this.strategyImpl = strategyImpl;
    }
}
