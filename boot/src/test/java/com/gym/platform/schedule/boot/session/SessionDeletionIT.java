package com.gym.platform.schedule.boot.session;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static io.restassured.RestAssured.given;

import java.time.OffsetDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.SessionDeletionDTO;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;

import io.restassured.http.ContentType;

public class SessionDeletionIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Delete an empty session successfully")
    public void deleteSesssionSuccessfully() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Juan", "SessionDeletionIT", "juan.SessionDeletionIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        given().when().delete("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Sessions with participants cannot be delete directly")
    public void cannotDeleteNotEmptySesssion() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Carlos", "SessionDeletionIT", "carlos.SessionDeletionIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var participant = createParticiant("Carlos SessionDeletionIT", "carlos.SessionDeletionIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        var json = given().when().delete("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.BAD_REQUEST.value()).extract().asString();

        var error = parse(json, ApiErrorDTO.class);
        assertEquals("NOT_EMPTY_SESSION", error.getErrorCode());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        // Remove participant registation from session
        given().when().delete("/api/v1/sessions/" + session.getId() + "/registrations/" + participant.getId())
                .then().statusCode(HttpStatus.OK.value());

        given().when().delete("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @DisplayName("Sessions with participants can be safely deleted by specifying each participant")
    public void notEmptySessionDeletion() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Seba", "SessionDeletionIT", "seba.SessionDeletionIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var participant = createParticiant("Seba SessionDeletionIT", "seba.SessionDeletionIT@gym.com");
        assertNotNull(participant.getId());

        var registration = registerParticipant(session.getId(), participant.getId());
        assertNotNull(registration.getParticipantId());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        var sessionDeletion = new SessionDeletionDTO();
        sessionDeletion.setParticipantIds(List.of(participant.getId()));
        given().contentType(ContentType.JSON).body(sessionDeletion)
                .when().delete("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.OK.value());

        given().when().get("/api/v1/sessions/" + session.getId())
                .then().statusCode(HttpStatus.NOT_FOUND.value());
    }
}
