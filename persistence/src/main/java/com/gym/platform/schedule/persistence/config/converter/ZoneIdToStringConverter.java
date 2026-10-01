package com.gym.platform.schedule.persistence.config.converter;

import java.time.ZoneId;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class ZoneIdToStringConverter implements Converter<ZoneId, String> {
    @Override
    public String convert(ZoneId source) {
        return source != null ? source.getId() : null;
    }
}
