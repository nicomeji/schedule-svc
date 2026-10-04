package com.gym.platform.schedule.rest.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gym.platform.schedule.application.service.SessionCrudSvc;
import com.gym.platform.schedule.application.usecase.AddParticipantToSession;
import com.gym.platform.schedule.application.usecase.CreateSession;
import com.gym.platform.schedule.application.usecase.DeleteSessionSvc;
import com.gym.platform.schedule.application.usecase.RemoveParticipantFromSession;
import com.gym.platform.schedule.domain.common.Page;
import com.gym.platform.schedule.rest.contract.SearchSessionDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.SessionDeletionDTO;
import com.gym.platform.schedule.rest.contract.SessionRegistrationDTO;
import com.gym.platform.schedule.rest.contract.common.PageDTO;
import com.gym.platform.schedule.rest.mapper.PageDtoMapper;
import com.gym.platform.schedule.rest.mapper.SessionDtoMapper;
import com.gym.platform.schedule.rest.validation.ValidSessionSearchFilters;

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
    private final CreateSession createSession;
    private final AddParticipantToSession addParticipantToSession;
    private final RemoveParticipantFromSession removeParticipantFromSession;
    private final DeleteSessionSvc deleteSessionSvc;
    private final SessionDtoMapper mapper;
    private final PageDtoMapper pageMapper;

    @PostMapping
    public ResponseEntity<SessionDTO> create(@RequestBody @Valid SessionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDto(createSession.createSession(mapper.toBaseData(dto))));
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> retrieveById(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String sessionId) {
        return service.retrieve(Long.parseLong(sessionId))
                .map(mapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(ResponseEntity.notFound()::build);
    }

    @GetMapping
    public ResponseEntity<PageDTO<SessionDTO>> search(
            @Valid @ValidSessionSearchFilters SearchSessionDTO filters) {
        Page<SessionDTO> results = service.search(mapper.toFiltersModel(filters)).map(mapper::toDto);
        return ResponseEntity.ok(pageMapper.toDto(results));
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteById(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String sessionId,
            @RequestBody(required = false) @Valid SessionDeletionDTO sessionDeletionDTO) {
        List<Long> participantIds = (sessionDeletionDTO != null)
                ? sessionDeletionDTO.getParticipantIds()
                : Collections.emptyList();
        deleteSessionSvc.deleteSession(Long.parseLong(sessionId), participantIds);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{sessionId}/registrations")
    public ResponseEntity<SessionRegistrationDTO> addParticipant(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String sessionId,
            @RequestBody @Valid SessionRegistrationDTO registration) {
        addParticipantToSession.addParticipant(Long.parseLong(sessionId), registration.getParticipantId());
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/{sessionId}/registrations")
    public ResponseEntity<List<SessionRegistrationDTO>> retrieveRegistrations(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String sessionId) {
        return ResponseEntity.ok(service.registrations(Long.parseLong(sessionId)).stream()
                .map(mapper::toRegistrationDto)
                .toList());
    }

    @DeleteMapping("/{sessionId}/registrations/{participantId}")
    public ResponseEntity<Void> removeParticipant(
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String sessionId,
            @PathVariable @Pattern(regexp = "^[0-9]+$", message = "ID must be a number") String participantId) {
        removeParticipantFromSession.removeParticipant(Long.parseLong(sessionId), Long.parseLong(participantId));
        return ResponseEntity.ok().build();
    }
}
