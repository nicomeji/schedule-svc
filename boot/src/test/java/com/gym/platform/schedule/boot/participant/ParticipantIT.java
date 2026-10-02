package com.gym.platform.schedule.boot.participant;

import com.gym.platform.schedule.boot.BaseIntegrationTest;
import com.gym.platform.schedule.rest.contract.ParticipantDTO;

import io.restassured.http.ContentType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParticipantIT extends BaseIntegrationTest {
    @Test
    @DisplayName("Create a new participant successfully")
    public void createParticipantSuccessfully() {
        ParticipantDTO participant = createParticiant("Juan ParticipantIT", "juan.ParticipantIT@gym.com");
        assertNotNull(participant.getId());
        assertEquals("Juan ParticipantIT", participant.getName());
        assertEquals("juan.ParticipantIT@gym.com", participant.getEmail());
    }

    @Test
    @DisplayName("Retrieve participant successfully")
    public void retrieveParticipantSuccessfully() {
        ParticipantDTO created = createParticiant("Juan2 ParticipantIT", "juan2.ParticipantIT@gym.com");
        assertNotNull(created.getId());

        var json = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/v1/participants/" + created.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .asString();
        ParticipantDTO retrieved = parse(json, ParticipantDTO.class);
        assertEquals(created.getId(), retrieved.getId());
        assertEquals("Juan2 ParticipantIT", retrieved.getName());
        assertEquals("juan2.ParticipantIT@gym.com", retrieved.getEmail());
    }
}
