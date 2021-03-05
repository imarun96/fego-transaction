package com.fego.transaction.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fego.transaction.common.constants.Constants;
import com.fego.transaction.dto.TransactionsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonConvertorUtil {
    private static final Logger logger = LoggerFactory.getLogger(JsonConvertorUtil.class);

    private JsonConvertorUtil() {

    }

    public static String convertObjectIntoJson(List<TransactionsDto> transactions) {
        ObjectMapper mapper = new ObjectMapper();
        String convertedJson = Constants.EMPTY;
        try {
            convertedJson = mapper.writeValueAsString(transactions);
        } catch (JsonProcessingException e) {
            logger.error("There is an error in converting the object to Json - {}", e.getMessage());
        }
        return convertedJson;
    }
}