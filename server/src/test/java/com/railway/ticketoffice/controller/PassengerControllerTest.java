package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.validator.PassengerValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Passenger passenger;
    private static long NOT_EXISTING_ID = 0L;

    @Before
    public void beforeEach() {
        passenger = Passenger.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .login("login")
                .build();
    }

    @Test
    @Rollback
    public void shouldReturnTrueWithValidUpdatePassengerData() throws Exception {
        String passengerJson = objectMapper.writeValueAsString(passenger);
        String expectedJson = "true";

        MvcResult content = mockMvc.perform(post("/api/passenger/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(passengerJson))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotExistingUpdatePassengerId() throws Exception {
        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY,
                String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID));
        String expectedJson = objectMapper.writeValueAsString(expectedCauseObject);
        passenger.setId(NOT_EXISTING_ID);
        String passengerJson = objectMapper.writeValueAsString(passenger);
        MvcResult content = mockMvc.perform(post("/api/passenger/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(passengerJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithAlreadyExistingUpdatePassengerLogin() throws Exception {
        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(PassengerValidator.KEY_LOGIN, PassengerValidator.VALIDATE_EXISTENCE_LOGIN_MESSAGE);
        String expectedJson = objectMapper.writeValueAsString(expectedCauseObject);
        passenger.setLogin("protasov1");
        String passengerJson = objectMapper.writeValueAsString(passenger);

        MvcResult content = mockMvc.perform(post("/api/passenger/update")
                .contentType(APPLICATION_JSON_UTF8)
                .content(passengerJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    @Rollback
    public void shouldReturnTrueWithValidRemovePassengerId() throws Exception {
        String expectedJson = "true";

        MvcResult content = mockMvc.perform(delete("/api/passenger/remove/4"))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotExistingRemovePassengerId() throws Exception {
        String expectedJson = String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, NOT_EXISTING_ID);

        MvcResult content = mockMvc.perform(delete("/api/passenger/remove/" + NOT_EXISTING_ID))
                .andExpect(status().isNotFound())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }
}
