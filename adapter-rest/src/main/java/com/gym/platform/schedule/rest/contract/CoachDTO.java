package com.gym.platform.schedule.rest.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CoachDTO {
        @Schema(description = "Registered coach ID", example = "123")
        private Long id;

        @Email
        @NotBlank
        @Schema(description = "Registered coach email", example = "juan@gym.com")
        private String email;

        @NotBlank
        @Schema(description = "Registered coach name", example = "Juan")
        private String name;
}
