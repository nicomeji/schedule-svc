package com.gym.platform.schedule.boot.session;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.CoachDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.common.ApiErrorDTO;
import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;

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
        TimeRangeDTO range = oneHourRange(OffsetDateTime.now().plusDays(1));

        CoachDTO coach = createCoach("Juan SessionCreationIT", "juan.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        SessionDTO.Session session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());
        assertEquals(coach.getId(), session.getSessionData().getCoachId());
        assertEquals(range.getFrom(), session.getSessionData().getStartTime());
        assertEquals(range.getTo(), session.getSessionData().getEndTime());
        assertEquals(12, session.getSessionData().getCapacity());
        assertEquals("location_1", session.getSessionData().getLocation());
    }

    @Test
    @DisplayName("Retrieve session successfully")
    public void retrieveSessionSuccessfully() {
        TimeRangeDTO range = oneHourRange(OffsetDateTime.now().plusDays(1));

        CoachDTO coach = createCoach("Juan2 SessionCreationIT", "juan2.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        SessionDTO.Session created = createSession(coach.getId(), range, 15);
        assertNotNull(created.getId());

        var json = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/sessions/" + created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        SessionDTO.Session retrieved = parse(json, SessionDTO.Session.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(coach.getId(), retrieved.getSessionData().getCoachId());
        assertEquals(range.getFrom(), retrieved.getSessionData().getStartTime());
        assertEquals(range.getTo(), retrieved.getSessionData().getEndTime());
        assertEquals(15, retrieved.getSessionData().getCapacity());
        assertEquals("location_1", retrieved.getSessionData().getLocation());
    }

    @Test
    @DisplayName("Create a new session successfully")
    public void cannotOverlapSesssions() {
        TimeRangeDTO range = oneHourRange(OffsetDateTime.now().plusDays(1));

        CoachDTO coach = createCoach("Juan3 SessionCreationIT", "juan3.SessionCreationIT@gym.com");
        assertNotNull(coach.getId());

        SessionDTO.Session session = createSession(coach.getId(), range, 12);
        assertNotNull(session.getId());

        SessionDTO.SessionCommonData sessionData = new SessionDTO.SessionCommonData();
        sessionData.setCoachId(coach.getId());
        sessionData.setLocation("location_1");
        sessionData.setStartTime(range.getFrom());
        sessionData.setEndTime(range.getTo());
        sessionData.setCapacity(12);

        SessionDTO.CreateSession createSession = new SessionDTO.CreateSession();
        createSession.setSessionData(sessionData);

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
