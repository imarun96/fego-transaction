package com.fego.transaction.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "application")
@PropertySource(value = "classpath:application-url.yml", factory = YamlPropertySourceFactory.class)
public class PropertyConfig {
    private Map<String, String> aws = new HashMap<>();
    private Map<String, String> integration = new HashMap<>();
    private Map<String, String> user = new HashMap<>();
    private String categorizationPartner;

    public Map<String, String> getIntegration() {
        return integration;
    }

    public void setIntegration(Map<String, String> integration) {
        this.integration = integration;
    }

    public Map<String, String> getAws() {
        return aws;
    }

    public void setAws(Map<String, String> aws) {
        this.aws = aws;
    }

    public Map<String, String> getUser() {
        return user;
    }

    public void setUser(Map<String, String> user) {
        this.user = user;
    }

    public String getCategorizationPartner() {
        return categorizationPartner;
    }

    public void setCategorizationPartner(String categorizationPartner) {
        this.categorizationPartner = categorizationPartner;
    }
}