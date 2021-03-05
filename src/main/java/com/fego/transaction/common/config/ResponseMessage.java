package com.fego.transaction.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "response-messages")
@PropertySource(value = "classpath:message.yaml", factory = YamlPropertySourceFactory.class)
public class ResponseMessage {

    Map<String, String> success = new HashMap<>();
    Map<String, String> error = new HashMap<>();

    public Map<String, String> getSuccess() {
        return success;
    }

    public void setSuccess(Map<String, String> success) {
        this.success = success;
    }

    public Map<String, String> getError() {
        return error;
    }

    public void setError(Map<String, String> error) {
        this.error = error;
    }

    public String getSuccessMessage(Integer code) {
        return getSuccess().get(Integer.toString(code));
    }

    public String getSuccessMessage(Integer code, String arg) {
        return MessageFormat.format(getSuccess().get(Integer.toString(code)), arg);
    }

    public String getSuccessMessage(Integer code, List<String> args) {
        return MessageFormat.format(getSuccess().get(Integer.toString(code)), args);
    }

    public String getSuccessMessage(String code) {
        return getSuccess().get(code);
    }

    public String getSuccessMessage(String code, String... args) {
        return MessageFormat.format(getSuccess().get(code), args);
    }

    public String getSuccessMessage(String code, List<String> args) {
        return MessageFormat.format(getSuccess().get(code), args);
    }

    public String getErrorMessage(Integer code) {
        return getError().get(Integer.toString(code));
    }

    public String getErrorMessage(Integer code, String arg) {
        return MessageFormat.format(getError().get(Integer.toString(code)), arg);
    }

    public String getErrorMessage(Integer code, List<String> args) {
        return MessageFormat.format(getError().get(Integer.toString(code)), args);
    }

    public String getErrorMessage(String code) {
        return getError().get(code);
    }

    public String getErrorMessage(Integer code, String... args) {
        return MessageFormat.format(getError().get(Integer.toString(code)), args);
    }

    public String getErrorMessage(String code, String... args) {
        return MessageFormat.format(getError().get(code), args);
    }

    public String getErrorMessage(String code, List<String> args) {
        return MessageFormat.format(getError().get(code), args);
    }
}