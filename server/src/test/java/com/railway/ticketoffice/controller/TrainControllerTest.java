package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import com.railway.ticketoffice.util.DateTimeUtil;
import com.railway.ticketoffice.validator.RequestValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.railway.ticketoffice.configuration.TokenProvider.getValidPassengerToken;
import static com.railway.ticketoffice.security.Constants.HEADER_STRING;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class TrainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void beforeEach() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    public void shouldReturnOkWithValidRequestData() throws Exception {
        List<TrainInfoDto> trainInfoDtoList = Arrays.asList(
                TrainInfoDto.builder()
                        .id(73L)
                        .coachNumber(8)
                        .firstStationName("Zaporizhzhya 1")
                        .lastStationName("Kyiv-Pasazhyrsky")
                        .departureDate(LocalDate.of(2019, 6, 12))
                        .arrivalDate(LocalDate.of(2019, 6, 13))
                        .departureTime(LocalTime.of(18, 20, 0))
                        .arrivalTime(LocalTime.of(5, 52, 0))
                        .duration("11:32")
                        .coachTypeInfoList(Arrays.asList(
                                CoachTypeInfoDto.builder()
                                        .coachTypeName("Compartment")
                                        .count(5L)
                                        .availablePlaces(180L)
                                        .totalPlaces(180L)
                                        .build(),
                                CoachTypeInfoDto.builder()
                                        .coachTypeName("Berth")
                                        .count(3L)
                                        .availablePlaces(160L)
                                        .totalPlaces(162L)
                                        .build()))
                        .build()
        );
        String expectedJson = objectMapper.writeValueAsString(trainInfoDtoList);

        MvcResult content = mockMvc
                .perform(get(
                        "/api/train/search?departureStation=1&destinationStation=2&departureDate=2019-06-12")
                        .header(HEADER_STRING, getValidPassengerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidDateFormat() throws Exception {
        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(RequestValidator.DATE_KEY, DateTimeUtil.VALIDATE_DATE_FORMAT_MESSAGE);
        String expectedJson = objectMapper.writeValueAsString(expectedCauseObject);

        MvcResult content = mockMvc.perform(get("/api/train/search?" +
                "departureStation=1&destinationStation=2&departureDate=2019/10/18")
                .header(HEADER_STRING, getValidPassengerToken(mockMvc)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }
}