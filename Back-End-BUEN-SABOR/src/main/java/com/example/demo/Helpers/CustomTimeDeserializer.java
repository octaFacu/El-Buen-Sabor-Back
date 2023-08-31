package com.example.demo.Helpers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.sql.Time;

public class CustomTimeDeserializer extends JsonDeserializer<Time> {


    @Override
    public Time deserialize(JsonParser jsonParser, DeserializationContext context)
            throws IOException, JsonProcessingException {
        long secondsSinceMidnight = jsonParser.getValueAsLong();
        return new Time(secondsSinceMidnight * 1000); // Convert seconds to milliseconds
    }
}
