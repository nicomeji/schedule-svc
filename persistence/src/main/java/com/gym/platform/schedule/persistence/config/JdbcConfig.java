package com.gym.platform.schedule.persistence.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;

import com.gym.platform.schedule.persistence.config.converter.StringToZoneOffsetConverter;
import com.gym.platform.schedule.persistence.config.converter.ZoneOffsetToStringConverter;

@Configuration
public class JdbcConfig extends AbstractJdbcConfiguration {
    @Override
    protected List<?> userConverters() {
        return Arrays.asList(
                new StringToZoneOffsetConverter(),
                new ZoneOffsetToStringConverter());
    }
}
