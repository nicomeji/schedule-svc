package com.gym.platform.schedule.boot.session;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;

public class SessionCreationIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Create a new session successfully")
    public void createSesssionSuccessfully() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Juan SessionCreationIT", "juan.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());
        assertEquals(coach.getId(), session.getCoachId());
        assertEquals(range.getFrom(), session.getStartTime());
        assertEquals(range.getTo(), session.getEndTime());
        assertEquals(12, session.getCapacity());
        assertEquals("location_1", session.getLocation());
    }

    @Test
    @DisplayName("Retrieve session successfully")
    public void retrieveSessionSuccessfully() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Juan2 SessionCreationIT", "juan2.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        var created = createSession(coach.getId(), range, 15);
        assertNotNull(created.getId());

        var json = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/sessions/" + created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        var retrieved = parse(json, SessionDTO.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(coach.getId(), retrieved.getCoachId());
        assertEquals(range.getFrom(), retrieved.getStartTime());
        assertEquals(range.getTo(), retrieved.getEndTime());
        assertEquals(15, retrieved.getCapacity());
        assertEquals("location_1", retrieved.getLocation());
    }

    @Test
    @DisplayName("Coach cannot participate in overlapping sessions")
    public void cannotOverlapSesssions() {
        var range = oneHourRange(OffsetDateTime.now().plusDays(1));

        var coach = createCoach("Juan3 SessionCreationIT", "juan3.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        var session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        var createSession = new SessionDTO();
        createSession.setCoachId(coach.getId());
        createSession.setLocation("location_1");
        createSession.setStartTime(range.getFrom());
        createSession.setEndTime(range.getTo());
        createSession.setCapacity(12);

        var json = given()
                .contentType(ContentType.JSON)
                .body(createSession)
                .when()
                .post("/api/v1/sessions")
                .then()
                .statusCode(HttpStatus.CONFLICT.value())
                .extract()
                .asString();

        var error = parse(json, ApiErrorDTO.class);
        assertEquals("SESSION_OVERLAP", error.getErrorCode());
    }
}
