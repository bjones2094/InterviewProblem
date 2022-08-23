package com.jbhunt.interview;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.jbhunt.interview.appointment.Appointment;
import com.jbhunt.interview.appointment.AppointmentDTO;
import com.jbhunt.interview.appointment.AppointmentRepository;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureWireMock(port = 9999)
public class AppointmentIT {

    @LocalServerPort
    private int port;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Before
    public void before() {
        wireMockServer.resetAll();
        appointmentRepository.deleteAll();
    }

    @Test
    public void testGetAppointments() throws IOException {
        // ARRANGE
        LocalDateTime appointmentStartTime = LocalDateTime.now().withNano(0);

        Appointment appointment = Appointment.builder()
                .customerId(1)
                .shopId(100)
                .appointmentStartTime(appointmentStartTime)
                .requestedService("Service 1")
                .isCompleted(false)
                .build();

        // create an appointment in the DB that we expect to be returned by the endpoint-under-test
        appointment = appointmentRepository.save(appointment);

        // stub our mock server to respond as the shop web service
        wireMockServer.stubFor(get(urlEqualTo("/shopservice/shops/100"))
            .willReturn(aResponse()
                    .withStatus(200)
                    .withBodyFile("shop-A1.json")
                    .withHeader("content-type", "application/json")));

        // ACT
        Response response = given()
                                .pathParam("customerId", 1)
                            .when()
                                .port(port)
                                .get("/customers/{customerId}/appointments")
                            .then()
                                .statusCode(200)
                                .extract().response();

        // ASSERT
        List<AppointmentDTO> actualList = objectMapper.readValue(response.getBody().asString(), new TypeReference<List<AppointmentDTO>>() {});
        assertThat(actualList.size(), is(1));
        AppointmentDTO actual = actualList.get(0);
        AppointmentDTO expected = AppointmentDTO.builder()
                .appointmentId(appointment.getAppointmentId())
                .customerId(1)
                .shopNumber("A1")
                .shopAddress("123 Main St")
                .requestedServices(Collections.singletonList("Service 1"))
                .appointmentStartTime(appointmentStartTime)
                .build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testCreateAppointment() throws JsonProcessingException {
        // ARRANGE

        // stub our mock server to respond as the shop web service
        wireMockServer.stubFor(get(urlEqualTo("/shopservice/shops/search?shopName=A1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBodyFile("shop-A1.json")
                        .withHeader("content-type", "application/json")));

        // create request body for appointment endpoint
        LocalDateTime appointmentStartTime = LocalDateTime.now().withNano(0);
        AppointmentDTO appointmentDTO = AppointmentDTO.builder()
                .customerId(1)
                .shopNumber("A1")
                .appointmentStartTime(appointmentStartTime)
                .requestedServices(Collections.singletonList("Service 1"))
                .build();

        // ACT
        // perform http request to appointment service under test
        given()
            .body(objectMapper.writeValueAsString(appointmentDTO))
            .contentType("application/json")
        .when()
            .port(port)
            .post("/appointments")
        .then()
            .statusCode(201);

        // ASSERT
        // verify appointment was created in database with expected values
        List<Appointment> actualListFromDB = appointmentRepository.findAllByCustomerIdAndIsCompleted(1, false);
        assertThat(actualListFromDB.size(), is(1));
        Appointment actual = actualListFromDB.get(0);
        assertThat(actual.getCustomerId(), is(1));
        assertThat(actual.getShopId(), is(100));
        assertThat(actual.getAppointmentStartTime(), is(appointmentStartTime));
        assertThat(actual.getRequestedService(), is("Service 1"));
        assertThat(actual.getIsCompleted(), is(false));
    }
}
