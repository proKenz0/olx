package org.example.operations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static <T> T parseJson(String stringObject, TypeReference<T> type) throws JsonProcessingException {
        return MAPPER.readValue(stringObject, type);
    }

}
