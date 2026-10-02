package com.gym.platform.schedule.rest.contract;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ParticipantDTO {
    @Schema(description = "Registered participant ID", example = "123")
    private Long id;

    @Email
    @NotBlank
    @Schema(description = "Registered participant email", example = "juan@gym.com")
    private String email;

    @NotBlank
    @Schema(description = "Registered participant name", example = "Juan")
    private String name;
}
