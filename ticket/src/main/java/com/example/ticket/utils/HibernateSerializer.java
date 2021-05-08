package com.example.ticket.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;

import org.springframework.kafka.support.JacksonUtils;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class HibernateSerializer<T> extends JsonSerializer<T> {

    private static ObjectMapper mapper = JacksonUtils.enhancedObjectMapper().registerModule(new Hibernate5Module());

    public HibernateSerializer() {
		super((JavaType) null, mapper);
    }    
    
}
