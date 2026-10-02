package com.gym.platform.schedule.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.platform.schedule.application.service.CoachCrudSvc;
import com.gym.platform.schedule.rest.contract.CoachDTO;
import com.gym.platform.schedule.rest.mapper.CoachDtoMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/coaches")
public class CoachController {
        private final CoachCrudSvc service;
        private final CoachDtoMapper mapper;

        @PostMapping
        @Operation(summary = "Coach creation", description = "Register new coach")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Coach registered successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid or missing input data")
        })
        public ResponseEntity<CoachDTO> create(@Valid @RequestBody CoachDTO dto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(mapper.toDto(service.create(mapper.toDomain(dto))));
        }

        @GetMapping("/{id}")
        public ResponseEntity<CoachDTO> retrieveById(
                        @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String id) {
                return service.retrieve(Long.parseLong(id))
                                .map(mapper::toDto)
                                .map(ResponseEntity::ok)
                                .orElseGet(ResponseEntity.notFound()::build);
        }
}
