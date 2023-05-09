package com.siw.uniroma3.it.siw_lavendetta.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class LocalDateConverter implements Converter<String, LocalDate> {

    private final DateTimeFormatter formatter;

    public LocalDateConverter(String dateFormatPattern) {
        this.formatter = DateTimeFormatter.ofPattern(dateFormatPattern);
    }

    @Override
    public LocalDate convert(String source) {
        if (source == null || source.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, formatter);
    }
}
