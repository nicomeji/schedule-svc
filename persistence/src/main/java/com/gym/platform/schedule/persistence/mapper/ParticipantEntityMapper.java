package com.gym.platform.schedule.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gym.platform.schedule.domain.model.Participant;
import com.gym.platform.schedule.persistence.model.ParticipantEntity;

@Mapper
public interface ParticipantEntityMapper {
    Participant.BaseData toBaseData(ParticipantEntity entity);

    // Used to create a new record
    @Mapping(target = "id", expression = "java(null)")
    ParticipantEntity toNewEntity(Participant.BaseData data);

    // Used to update an existing record
    ParticipantEntity toExistingEntity(Participant data);
}
