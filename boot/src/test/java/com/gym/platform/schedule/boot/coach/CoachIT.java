package com.gym.platform.schedule.boot.coach;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.CoachDTO;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CoachIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Create a new coach successfully")
    public void createCoachSuccessfully() {
        var coach = createCoach("Juan", "CoachIT", "juan.CoachIT@gym.com");
        assertNotNull(coach.getId());
        assertEquals("Juan", coach.getFirstName());
        assertEquals("CoachIT", coach.getLastName());
        assertEquals("juan.CoachIT@gym.com", coach.getEmail());
    }

    @Test
    @DisplayName("Retrieve created coach successfully")
    public void retrieveCoachSuccessfully() {
        var created = createCoach("Juan2", "CoachIT", "juan2.CoachIT@gym.com");
        assertNotNull(created.getId());

        var json = given().contentType(ContentType.JSON)
                .when().get("/api/v1/coaches/" + created.getId())
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();

        var retrieved = parse(json, CoachDTO.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("Juan2", retrieved.getFirstName());
        assertEquals("CoachIT", retrieved.getLastName());
        assertEquals("juan2.CoachIT@gym.com", retrieved.getEmail());
    }

    @Test
    @Sql("/sql/insert_legacy_coach.sql")
    @DisplayName("Retrieve old version coach successfully")
    public void retrieveOldVersionCoachSuccessfully() {
        var json = given().contentType(ContentType.JSON)
                .when().get("/api/v1/coaches/" + 1)
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();

        var retrieved = parse(json, CoachDTO.class);
        assertEquals(1, retrieved.getId());
        assertEquals("Old Version", retrieved.getFirstName());
        assertEquals("UPDATE YOUR LAST_NAME", retrieved.getLastName());
        assertEquals("oldCoachVersion@test.com", retrieved.getEmail());
    }

    @Test
    @Sql("/sql/insert_legacy_coach.sql")
    @DisplayName("Old and new coach versions works together")
    public void manageDifferentCoachVersionsSuccessfully() {
        var created = createCoach("Juan2", "CoachIT", "juan2.CoachIT@gym.com");
        assertNotNull(created.getId());

        var newVersionJson = given().contentType(ContentType.JSON)
                .when().get("/api/v1/coaches/" + created.getId())
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();
        var newVersion = parse(newVersionJson, CoachDTO.class);

        var oldVersionJson = given().contentType(ContentType.JSON)
                .when().get("/api/v1/coaches/" + 1)
                .then().statusCode(HttpStatus.OK.value())
                .extract().asString();
        var oldVersion = parse(oldVersionJson, CoachDTO.class);

        assertEquals(1, oldVersion.getId());
        assertEquals(created.getId(), newVersion.getId());
    }
}
