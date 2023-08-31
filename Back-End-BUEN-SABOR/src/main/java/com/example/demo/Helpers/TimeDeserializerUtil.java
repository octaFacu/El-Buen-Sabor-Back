package com.example.demo.Helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.sql.Time;

public class TimeDeserializerUtil {
    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Time.class, new CustomTimeDeserializer());
        objectMapper.registerModule(module);
    }

    public static Time deserializeTime(String jsonTime) {
        try {
            return objectMapper.readValue(jsonTime, Time.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
