package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.persistence.model.CoachEntity;

@Mapper
public interface CoachEntityMapper {
    Coach.BaseData toBaseData(CoachEntity entity);

    // Used to create a new record
    @Mapping(target = "id", expression = "java(null)")
    CoachEntity toNewEntity(Coach.BaseData data);

    // Used to update an existing record
    CoachEntity toExistingEntity(Coach data);
}
