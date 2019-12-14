package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;

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
public class CoachControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnCoachInfoDtoWithValidRequestData() throws Exception {
        CoachInfoDto coachInfoDto = CoachInfoDto.builder()
                .id(4L)
                .typeId(2L)
                .name("Seating second class")
                .number(1)
                .availablePlaces(80)
                .totalPlaces(80)
                .bookedPlaces(Collections.emptyList())
                .build();
        String expectedJson = objectMapper.writeValueAsString(coachInfoDto);

        MvcResult content = mockMvc
                .perform(get("/api/coach/search?trainId=732&departureDate=2019-08-31&coachNumber=1")
                        .header(HEADER_STRING, getValidPassengerToken(mockMvc)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidRequestData() throws Exception {
        MvcResult content = mockMvc
                .perform(get("/api/coach/search?trainId=732&departureDate=2019.10.18&coachNumber=1")
                        .header(HEADER_STRING, getValidPassengerToken(mockMvc)))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(DateTimeUtil.VALIDATE_DATE_FORMAT_MESSAGE, resultJson);
    }
}