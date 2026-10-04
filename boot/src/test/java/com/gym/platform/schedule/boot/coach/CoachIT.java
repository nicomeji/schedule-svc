package com.gym.platform.schedule.boot.coach;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.CoachDTO;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoachIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Create a new coach successfully")
    public void createCoachSuccessfully() {
        var coach = createCoach("Juan CoachIT", "juan.CoachIT@gym.com");
        assertNotNull(coach.getId());
        assertEquals("Juan CoachIT", coach.getName());
        assertEquals("juan.CoachIT@gym.com", coach.getEmail());
    }

    @Test
    @DisplayName("Retrieve coach successfully")
    public void retrieveCoachSuccessfully() {
        var created = createCoach("Juan2 CoachIT", "juan2.CoachIT@gym.com");
        assertNotNull(created.getId());

        var json = given().contentType(ContentType.JSON)
                .when().get("/api/v1/coaches/" + created.getId())
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();

        var retrieved = parse(json, CoachDTO.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("Juan2 CoachIT", retrieved.getName());
        assertEquals("juan2.CoachIT@gym.com", retrieved.getEmail());
    }
}
