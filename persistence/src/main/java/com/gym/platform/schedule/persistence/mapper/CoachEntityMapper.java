package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Coach;
import com.gym.platform.schedule.persistence.model.CoachEntity;

@Mapper
public interface CoachEntityMapper {
    default Coach.BaseData toBaseData(CoachEntity entity) {
        if (entity.name() == null) {
            return new Coach.BaseData(entity.email(), entity.firstName(), entity.lastName());
        } else {
            return new Coach.BaseData(entity.email(), entity.name(), "UPDATE YOUR LAST_NAME");
        }
    }

    // Used to create a new record
    @Mapping(target = "id", expression = "java(null)")
    @Mapping(target = "name", expression = "java(null)")
    CoachEntity toNewEntity(Coach.BaseData data);

    // Used to update an existing record
    @Mapping(target = "name", expression = "java(null)")
    CoachEntity toExistingEntity(Coach data);
}
