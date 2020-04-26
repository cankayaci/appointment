package com.myhospital.appointment.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(String s) {
        return LocalDate.parse(
                s, DateTimeFormatter.ofPattern("MM/dd/yyyy")).atStartOfDay();
    }
}
