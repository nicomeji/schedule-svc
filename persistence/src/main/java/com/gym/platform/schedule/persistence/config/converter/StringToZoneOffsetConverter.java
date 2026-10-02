package com.gym.platform.schedule.persistence.config.converter;

import java.time.ZoneOffset;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToZoneOffsetConverter implements Converter<String, ZoneOffset> {
    @Override
    public ZoneOffset convert(String source) {
        return source != null ? ZoneOffset.of(source) : null;
    }
}
