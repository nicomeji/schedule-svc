package com.gym.platform.schedule.rest.contract;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.gym.platform.schedule.rest.contract.common.SearchedPageDTO;
import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;
import com.gym.platform.schedule.rest.validation.ValidTimeRange;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class SessionDTO {
    @Data
    public static class SessionCommonData {
        @NotNull
        private Long coachId;

        @NotNull
        private OffsetDateTime startTime;

        @NotNull
        private OffsetDateTime endTime;

        @NotNull
        private Integer capacity;

        @NotNull
        private String location;
    }

    @Data
    public static class CreateSession {
        @Valid
        @JsonUnwrapped
        private SessionCommonData sessionData;
    }

    @Data
    public static class Session {
        private Long id;

        @JsonUnwrapped
        private SessionCommonData sessionData;
    }

    @Data
    public static class SessionDetails {
        @JsonUnwrapped
        private Session sessionData;

        private List<String> participants;
    }

    @Data
    public static class SearchSessionDTO {
        private List<@NotNull(message = "'coach_id' cannot be null") Long> coachIds;

        @Valid
        @ValidTimeRange
        private TimeRangeDTO startTime;

        @Valid
        private SearchedPageDTO page = new SearchedPageDTO();
    }
}
