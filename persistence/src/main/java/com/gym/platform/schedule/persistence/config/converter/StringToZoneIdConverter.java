package com.gym.platform.schedule.persistence.config.converter;

import java.time.ZoneId;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class StringToZoneIdConverter implements Converter<String, ZoneId> {
    @Override
    public ZoneId convert(String source) {
        return source != null ? ZoneId.of(source) : null;
    }
}
