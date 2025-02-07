package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.PassengerDto;
import com.railway.ticketoffice.util.PageUtil;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class PassengerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Passenger passenger;
    private static long NOT_EXISTING_ID = 0L;

    @Before
    public void beforeEach() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        passenger = Passenger.builder()
                .id(1L)
                .firstName("FirstName")
                .lastName("LastName")
                .login("login")
                .build();
    }

    @Test
    public void shouldReturnPassengerPageWithValidPageNumber() throws Exception {
        PageableDto<PassengerDto> pageableDto = new PageableDto<>(
                Arrays.asList(PassengerDto.builder()
                                .id(1L)
                                .firstName("Aleksandra")
                                .lastName("Kibko")
                                .login("tiver69")
                                .totalTickets(11L)
                                .lastActive(LocalDate.of(2019, 11, 16))
                                .build(),
                        PassengerDto.builder()
                                .id(2L)
                                .firstName("Evgenia")
                                .lastName("Savina")
                                .login("shipper1232")
                                .totalTickets(1L)
                                .lastActive(LocalDate.of(2019, 8, 31))
                                .build(),
                        PassengerDto.builder()
                                .id(3L)
                                .firstName("Vladislav")
                                .lastName("Protasov")
                                .login("protasov1")
                                .totalTickets(0L)
                                .lastActive(null)
                                .build(),
                        PassengerDto.builder()
                                .id(4L)
                                .firstName("Test")
                                .lastName("Test")
                                .login("test11")
                                .totalTickets(2L)
                                .lastActive(LocalDate.of(2019, 8, 31))
                                .build()
                ),
                1);
        String expectedJson = objectMapper.writeValueAsString(pageableDto);

        MvcResult content = mockMvc.perform(get("/api/passenger/page?page=0"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidPassengerId() throws Exception {
        int notExistingPage = 10;
        String expectedJson = String.format(PageUtil.EXIST_MESSAGE_FORMAT, notExistingPage);

        MvcResult content = mockMvc.perform(get("/api/passenger/page?page=" + notExistingPage))
                .andExpect(status().isNotFound())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
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
