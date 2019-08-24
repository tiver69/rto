package com.railway.ticketoffice.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnOkIfValidPassengerId() throws Exception {
        mockMvc.perform(get("/api/ticket?passengerId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    public void shouldReturnBadRequestIfNotValidPassengerId() throws Exception {
        mockMvc.perform(get("/api/ticket?passengerId=0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnOkIfValidParam() throws Exception {
        mockMvc.perform(get("/api/ticket/price?trainId=732&trainCoachId=20&departureStationId=2&destinationStationId=1"))
                .andExpect(status().isOk())
                .andExpect((content().contentType(APPLICATION_JSON_UTF8)));
    }

    @Test
    public void shouldReturnBadRequestIfNotValidParam() throws Exception {
        mockMvc.perform(get("/api/ticket/price?trainId=72332&trainCoachId=20&departureStationId=1&destinationStationId=2"))
                .andExpect(status().isBadRequest());
    }
}