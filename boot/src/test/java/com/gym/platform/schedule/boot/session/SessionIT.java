package com.gym.platform.schedule.boot.session;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.CoachDTO;
import com.gym.platform.schedule.rest.contract.SessionDTO;
import com.gym.platform.schedule.rest.contract.common.PageDTO;
import com.gym.platform.schedule.rest.contract.common.TimeRangeDTO;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Create a new session successfully")
    public void createSesssionSuccessfully() {
        TimeRangeDTO range = oneHourRange(OffsetDateTime.now().plusDays(1));

        CoachDTO coach = createCoach("Juan SessionIT", "juan.SessionIT@gym.com");
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

        CoachDTO coach = createCoach("Juan2 SessionIT", "juan2.SessionIT@gym.com");
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
        SessionDTO.Session  retrieved = parse(json, SessionDTO.Session.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals(coach.getId(), retrieved.getSessionData().getCoachId());
        assertEquals(range.getFrom(), retrieved.getSessionData().getStartTime());
        assertEquals(range.getTo(), retrieved.getSessionData().getEndTime());
        assertEquals(15, retrieved.getSessionData().getCapacity());
        assertEquals("location_1", retrieved.getSessionData().getLocation());
    }

    @Test
    @DisplayName("Search sessions of a coach")
    public void searchSesssionByCoach() {
        TimeRangeDTO range1 = oneHourRange(OffsetDateTime.now().plusDays(1));
        TimeRangeDTO range2 = oneHourRange(OffsetDateTime.now().plusDays(2));

        CoachDTO coach1 = createCoach("Carlos1 SessionIT", "c1.SessionIT@gym.com");
        CoachDTO coach2 = createCoach("Carlos2 SessionIT", "c2.SessionIT@gym.com");

        SessionDTO.Session session1 = createSession(coach1.getId(), range1, 12);
        SessionDTO.Session session2 = createSession(coach1.getId(), range2, 12);
        @SuppressWarnings("unused")
        SessionDTO.Session session3 = createSession(coach2.getId(), range1, 12);

        Map<String, String> queryParams = Map.of("coachIds", coach1.getId().toString());
        PageDTO<SessionDTO.Session> sessions = searchSessions(queryParams);
        assertNotNull(sessions);
        assertEquals(2, sessions.getTotalElements());
        assertEquals(0, sessions.getSearchedPage().getOffset());
        assertEquals(10, sessions.getSearchedPage().getSize());
        assertEquals(2, sessions.getSearchedElements().size());
        assertEquals(coach1.getId(), sessions.getSearchedElements().get(0).getSessionData().getCoachId());
        assertEquals(coach1.getId(), sessions.getSearchedElements().get(1).getSessionData().getCoachId());

        var sessionIds = sessions.getSearchedElements().stream().map(s -> s.getId()).collect(Collectors.toSet());
        assertEquals(Set.of(session1.getId(), session2.getId()), sessionIds);
    }

    @Test
    @DisplayName("Search sessions by start time")
    public void searchSesssionByStartTime() {
        TimeRangeDTO range1 = oneHourRange(OffsetDateTime.now().plusDays(1));
        TimeRangeDTO range2 = oneHourRange(OffsetDateTime.now().plusDays(2));

        CoachDTO coach1 = createCoach("Ale1 SessionIT", "a1.SessionIT@gym.com");

        @SuppressWarnings("unused")
        SessionDTO.Session session1 = createSession(coach1.getId(), range1, 12);
        SessionDTO.Session session2 = createSession(coach1.getId(), range2, 12);

        var queryParams = Map.of(
            "coachIds", coach1.getId().toString(),
            "startTime.From", OffsetDateTime.now().plusDays(2).minusHours(1).toString());
        PageDTO<SessionDTO.Session> sessions = searchSessions(queryParams);
        assertNotNull(sessions);
        assertEquals(1, sessions.getTotalElements());
        assertEquals(0, sessions.getSearchedPage().getOffset());
        assertEquals(10, sessions.getSearchedPage().getSize());
        assertEquals(1, sessions.getSearchedElements().size());
        assertEquals(session2.getId(), sessions.getSearchedElements().get(0).getId());
    }
}
