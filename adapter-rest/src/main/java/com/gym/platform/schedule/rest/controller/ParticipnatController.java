package com.gym.platform.schedule.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.platform.schedule.application.service.ParticipantCrudSvc;
import com.gym.platform.schedule.rest.contract.ParticipantDTO;
import com.gym.platform.schedule.rest.mapper.ParticipantDtoMapper;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/participnats")
public class ParticipnatController {
    private final ParticipantCrudSvc service;
    private final ParticipantDtoMapper mapper;

    @PostMapping
    public ResponseEntity<ParticipantDTO> create(@RequestBody ParticipantDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(service.create(mapper.toDomain(dto))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> retrieveById(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String id) {
        return service.retrieve(Long.parseLong(id))
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }
}
