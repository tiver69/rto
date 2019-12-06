package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.railway.ticketoffice.domain.*;
import com.railway.ticketoffice.dto.PageableDto;
import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.validator.PassengerValidator;
import com.railway.ticketoffice.validator.TrainCoachValidator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    private Ticket ticket;

    @Before
    public void beforeEach() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        ticket = Ticket.builder()
                .passenger(Passenger.builder().id(1L).build())
                .departureStation(Station.builder().id(2L).build())
                .destinationStation(Station.builder().id(1L).build())
                .departureDate(LocalDate.of(2019, 11, 29))
                .arrivalDate(LocalDate.of(2019, 11, 29))
                .trainCoach(TrainCoach.builder().id(2L).train(Train.builder().id(732L).build()).build())
                .place(11)
                .build();
    }

    @Test
    public void shouldReturnTicketPageWithValidPassengerId() throws Exception {
        PageableDto<TicketDto> pageableDto = new PageableDto<>(
                Arrays.asList(TicketDto.builder()
                        .id(2L)
                        .departureStation("Kyiv-Pasazhyrsky")
                        .destinationStation("Zaporizhzhya 1")
                        .departureDateTime("2019-08-31 07:07")
                        .arrivalDateTime("2019-08-31 14:35")
                        .trainId(732L)
                        .coachNumber(5)
                        .place(18)
                        .price(472)
                        .build()),
                1);
        String expectedJson = objectMapper.writeValueAsString(pageableDto);

        MvcResult content = mockMvc.perform(get("/api/ticket/page?passengerId=2&page=0&isActive=false"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidPassengerId() throws Exception {
        int notExistingId = 0;
        String expectedJson = String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, notExistingId);

        MvcResult content = mockMvc.perform(get("/api/ticket/page?" +
                "passengerId=" + notExistingId + "&page=1&isActive=false"))
                .andExpect(status().isNotFound())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    @Rollback
    public void shouldReturnTicketWithValidSaveTicketData() throws Exception {
        String ticketJson = objectMapper.writeValueAsString(ticket);
        ticket.setId(15L);
        ticket.setPrice(472);
        String expectedJson = objectMapper.writeValueAsString(ticket);

        MvcResult content = mockMvc.perform(post("/api/ticket/save")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ticketJson))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidSaveTicketData() throws Exception {
        HashMap<String, String> expectedCauseObject = new HashMap<>();
        expectedCauseObject.put(TrainCoachValidator.KEY,
                String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT, 2, 0));
        expectedCauseObject.put(PassengerValidator.KEY, String.format(PassengerValidator.EXIST_MESSAGE_FORMAT, 0));
        String expectedJson = objectMapper.writeValueAsString(expectedCauseObject);
        ticket.setPassenger(Passenger.builder().id(0L).build());
        ticket.setTrainCoach(TrainCoach.builder().id(2L).train(Train.builder().id(0L).build()).build());
        String ticketJson = objectMapper.writeValueAsString(ticket);

        MvcResult content = mockMvc.perform(post("/api/ticket/save")
                .contentType(APPLICATION_JSON_UTF8)
                .content(ticketJson))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnPriceWithValidRequestData() throws Exception {
        String expectedJson = "402";

        MvcResult content = mockMvc.perform(get("/api/ticket/price?" +
                "trainId=732&trainCoachId=20&departureStationId=2&destinationStationId=1"))
                .andExpect(status().isOk())
                .andExpect((content().contentType(APPLICATION_JSON_UTF8)))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }

    @Test
    public void shouldReturnBadRequestWithNotValidRequestData() throws Exception {
        //hardcoded string message - may fail test if changes will occur in code
        String expectedJson = "Unexpected error while counting ticket price";

        MvcResult content = mockMvc.perform(get("/api/ticket/price?" +
                "trainId=72332&trainCoachId=20&departureStationId=1&destinationStationId=2"))
                .andExpect(status().isBadRequest())
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }
}