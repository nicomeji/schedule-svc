package com.gym.platform.schedule.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.platform.schedule.application.service.SessionCrudSvc;
import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.common.PageDTO;
import com.gym.platform.schedule.rest.mapper.PageDtoMapper;
import com.gym.platform.schedule.rest.mapper.SessionDtoMapper;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {
    private final SessionCrudSvc service;
    private final SessionDtoMapper mapper;
    private final PageDtoMapper pageMapper;

    @PostMapping
    public ResponseEntity<SessionDTO.Session> create(@Valid @RequestBody SessionDTO.CreateSession dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toSessionDTO(service.create(mapper.toModel(dto))));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO.Session> retrieveById(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String id) {
        return service.retrieve(Long.parseLong(id))
                .map(mapper::toSessionDTO)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping
    public ResponseEntity<PageDTO<SessionDTO.Session>> search(@Valid SessionDTO.SearchSessionDTO filters) {
        Page<SessionDTO.Session> results = service.search(mapper.toModel(filters)).map(mapper::toSessionDTO);
        return ResponseEntity.ok(pageMapper.toDto(results));
    }
}
