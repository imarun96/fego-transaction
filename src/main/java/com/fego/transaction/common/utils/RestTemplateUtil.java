package com.fego.transaction.common.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class RestTemplateUtil {

    private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);
    private static final String NO_DATA_FOUND_MSG = "No data found {}";
    private static final String REST_CLIENT_EXCEPTION_MSG = "rest client exception - {}";
    private final RestTemplate restTemplate;
    private ObjectMapper objectMapper;

    @Autowired
    public RestTemplateUtil(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    public <T> T getForEntity(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);
            JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
            return readValue(response, javaType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                logger.info(NO_DATA_FOUND_MSG, url);
            } else {
                logger.info(REST_CLIENT_EXCEPTION_MSG, exception.getMessage());
            }
        }
        return null;
    }

    public <T> List<T> getForList(Class<T> clazz, String url, Object... uriVariables) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class, uriVariables);
            objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            CollectionType collectionType = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return readValue(response, collectionType);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                logger.info(NO_DATA_FOUND_MSG, url);
            } else {
                logger.info(REST_CLIENT_EXCEPTION_MSG, exception.getMessage());
            }
        }
        return Collections.emptyList();
    }

    public <T, R> T postForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class, uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(response, javaType);
    }

    public <T, R> T putForEntity(Class<T> clazz, String url, R body, Object... uriVariables) {
        HttpEntity<R> request = new HttpEntity<>(body);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class,
                uriVariables);
        JavaType javaType = objectMapper.getTypeFactory().constructType(clazz);
        return readValue(response, javaType);
    }

    private <T> T readValue(@NotNull ResponseEntity<String> response, JavaType javaType) {
        T result = null;
        if (response.getStatusCode().equals(HttpStatus.OK) || response.getStatusCode().equals(HttpStatus.CREATED)) {
            try {
                if (response.getBody().startsWith("{\"message\":")) {
                    result = objectMapper.readValue(response.getBody().substring(25, response.getBody().length() - 39),
                            javaType);
                } else {
                    result = objectMapper.readValue(response.getBody(), javaType);
                }
            } catch (IOException e) {
                logger.info(e.getMessage());
            }
        } else {
            logger.info(NO_DATA_FOUND_MSG, response.getStatusCode());
        }
        return result;
    }
}