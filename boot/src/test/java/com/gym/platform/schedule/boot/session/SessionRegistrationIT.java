package com.gym.platform.schedule.boot.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.OffsetDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.SessionRegistrationDTO;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;

import io.restassured.http.ContentType;

public class SessionRegistrationIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Register a new participant to a session successfully")
    public void registerParticipantSuccessfully() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Juan", "SessionRegistrationIT", "juan.SessionRegistrationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var participant = createParticiant("Juan SessionRegistrationIT", "juan.SessionRegistrationIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());
    }

    @Test
    @DisplayName("Retrieve session registrations")
    public void retrieveSesssionRegistrationsSuccessfully() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Ana", "SessionRegistrationIT", "ana.SessionRegistrationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 10);
        assertNotNull(session.getId());

        var participant1 = createParticiant("Ana1 SessionRegistrationIT", "ana1.SessionRegistrationIT@gym.com");
        assertNotNull(participant1.getId());
        var participant2 = createParticiant("Ana2 SessionRegistrationIT", "ana2.SessionRegistrationIT@gym.com");
        assertNotNull(participant2.getId());

        var registration1 = registerParticipant(session.getId(), participant1.getId());
        assertNotNull(registration1.getParticipantId());
        var registration2 = registerParticipant(session.getId(), participant2.getId());
        assertNotNull(registration2.getParticipantId());

        var registrations = retrieveRegistrations(session.getId());
        assertEquals(2, registrations.size());
        var participantIds = registrations.stream().map(SessionRegistrationDTO::getParticipantId).toList();
        assertTrue(participantIds.contains(participant1.getId()));
        assertTrue(participantIds.contains(participant2.getId()));
    }

    @Test
    @DisplayName("Delete participant registration")
    public void removeRegistration() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Clara", "SessionRegistrationIT", "clara.SessionRegistrationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var participant = createParticiant("Clara SessionRegistrationIT", "clara.SessionRegistrationIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());

        var registrations = retrieveRegistrations(session.getId());
        assertEquals(1, registrations.size());
        assertEquals(participant.getId(), registrations.get(0).getParticipantId());

        given().when().delete("/api/v1/sessions/" + session.getId() + "/registrations/" + participant.getId())
                .then().statusCode(HttpStatus.OK.value());

        registrations = retrieveRegistrations(session.getId());
        assertEquals(0, registrations.size());
    }

    @Test
    @DisplayName("Participant cannot register to a session twice")
    public void cannotDuplicateRegistration() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Carlos", "SessionRegistrationIT", "carlos.SessionRegistrationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var participant = createParticiant("Carlos SessionRegistrationIT", "carlos.SessionRegistrationIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());

        var duplicated = new SessionRegistrationDTO();
        duplicated.setParticipantId(participant.getId());

        var json = given().contentType(ContentType.JSON).body(registration)
                .when().post("/api/v1/sessions/" + session.getId() + "/registrations")
                .then().statusCode(HttpStatus.CONFLICT.value())
                .extract().asString();

        var error = parse(json, ApiErrorDTO.class);
        assertEquals("DUPLICATE_SESSION_REGISTRATION", error.getErrorCode());
    }

    @Test
    @DisplayName("Participant cannot participate in overlapping sessions")
    public void cannotOverlapRegistration() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach1 = createCoach("Ale1", "SessionRegistrationIT", "ale1.SessionRegistrationIT@gym.com");
        assertNotNull(coach1.getId());
        var coach2 = createCoach("Ale2", "SessionRegistrationIT", "ale2.SessionRegistrationIT@gym.com");
        assertNotNull(coach2.getId());

        var session1 = createSession(coach1.getId(), range, 12);
        assertNotNull(session1.getId());
        var session2 = createSession(coach2.getId(), range, 12);
        assertNotNull(session2.getId());

        var participant = createParticiant("Ale SessionRegistrationIT", "ale.SessionRegistrationIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session1.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());

        var overlaped = new SessionRegistrationDTO();
        overlaped.setParticipantId(participant.getId());

        var json = given().contentType(ContentType.JSON).body(registration)
                .when().post("/api/v1/sessions/" + session2.getId() + "/registrations")
                .then().statusCode(HttpStatus.CONFLICT.value())
                .extract().asString();

        var error = parse(json, ApiErrorDTO.class);
        assertEquals("SESSION_OVERLAP", error.getErrorCode());
    }

    @Test
    @DisplayName("Participant cannot be registered to a full session")
    public void cannotExceedSesssionCapacity() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Sara", "SessionRegistrationIT", "sara.SessionRegistrationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 1);
        assertNotNull(session.getId());

        var participant1 = createParticiant("Sara1 SessionRegistrationIT", "sara1.SessionRegistrationIT@gym.com");
        assertNotNull(participant1.getId());
        var participant2 = createParticiant("Sara2 SessionRegistrationIT", "sara2.SessionRegistrationIT@gym.com");
        assertNotNull(participant2.getId());

        var registration = registerParticipant(session.getId(), participant2.getId());
        assertNotNull(registration.getParticipantId());

        var json = given().contentType(ContentType.JSON).body(registration)
                .when().post("/api/v1/sessions/" + session.getId() + "/registrations")
                .then().statusCode(HttpStatus.CONFLICT.value())
                .extract().asString();

        var error = parse(json, ApiErrorDTO.class);
        assertEquals("EXCEEDED_SESSION_REGISTRATION", error.getErrorCode());
    }
}
