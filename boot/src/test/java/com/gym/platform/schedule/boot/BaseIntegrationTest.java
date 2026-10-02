package com.gym.platform.schedule.boot;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gym.platform.schedule.rest.contract.CoachDTO;
import com.gym.platform.schedule.rest.contract.ParticipantDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.common.PageDTO;
import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;

import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseIntegrationTest {
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        RestAssured.config = RestAssuredConfig.config()
                .objectMapperConfig(new ObjectMapperConfig()
                        .jackson2ObjectMapperFactory((cls, charset) -> objectMapper));
    }

    protected ParticipantDTO createParticiant(String name, String email) {
        ParticipantDTO participant = new ParticipantDTO();
        participant.setName(name);
        participant.setEmail(email);

        var json = given()
                .contentType(ContentType.JSON)
                .body(participant)
                .when()
                .post("/api/v1/participants")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .asString();
        return parse(json, ParticipantDTO.class);
    }

    protected CoachDTO createCoach(String name, String email) {
        CoachDTO coach = new CoachDTO();
        coach.setName(name);
        coach.setEmail(email);

        var json = given()
                .contentType(ContentType.JSON)
                .body(coach)
                .when()
                .post("/api/v1/coaches")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .asString();
        return parse(json, CoachDTO.class);
    }

    protected SessionDTO.Session createSession(Long coachId, TimeRangeDTO range, Integer capacity) {
        SessionDTO.SessionCommonData sessionData = new SessionDTO.SessionCommonData();
        sessionData.setCoachId(coachId);
        sessionData.setLocation("location_1");
        sessionData.setStartTime(range.getFrom());
        sessionData.setEndTime(range.getTo());
        sessionData.setCapacity(capacity);

        SessionDTO.CreateSession createSession = new SessionDTO.CreateSession();
        createSession.setSessionData(sessionData);

        var json = given()
                .contentType(ContentType.JSON)
                .body(createSession)
                .when()
                .post("/api/v1/sessions")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .asString();
        return parse(json, SessionDTO.Session.class);
    }

    protected PageDTO<SessionDTO.Session> searchSessions(Map<String, String> params) {
        var json = given()
                .queryParams(params)
                .when()
                .get("/api/v1/sessions")
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        return parse(json, new TypeReference<PageDTO<SessionDTO.Session>>() {
            });
    }

    protected <T> T parse(String json, Class<T> toType) {
        try {
            return objectMapper.readValue(json, toType);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    protected <T> T parse(String json, TypeReference<T> toType) {
        try {
            return objectMapper.readValue(json, toType);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }

    protected TimeRangeDTO oneHourRange(OffsetDateTime from) {
        from = from.truncatedTo(ChronoUnit.SECONDS);
        var range = new TimeRangeDTO();
        range.setFrom(from);
        range.setTo(from.plusHours(1));
        return range;
    }
}
