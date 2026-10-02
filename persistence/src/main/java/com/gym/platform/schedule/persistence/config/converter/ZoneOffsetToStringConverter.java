package com.gym.platform.schedule.persistence.config.converter;

import java.time.ZoneOffset;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ZoneOffsetToStringConverter implements Converter<ZoneOffset, String> {
    @Override
    public String convert(ZoneOffset source) {
        return source != null ? source.getId() : null;
    }
}
