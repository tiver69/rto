package com.railway.ticketoffice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.ticketoffice.dto.StationSelectDto;
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

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class StationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void shouldReturnStationsList() throws Exception {
        List<StationSelectDto> stationSelectDtoList = Arrays.asList(
                new StationSelectDto(1L,"Zaporizhzhya 1"),
                new StationSelectDto(2L,"Kyiv-Pasazhyrsky"),
                new StationSelectDto(3L,"Im. T. Shevchenka"),
                new StationSelectDto(4L,"Znamyanka Pasazhyrska"),
                new StationSelectDto(5L,"Oleksandriya"),
                new StationSelectDto(6L,"Pyatykhatky"),
                new StationSelectDto(7L,"Kamianske-Pasazhyrske"),
                new StationSelectDto(8L,"Dnipro-Holovny"),
                new StationSelectDto(9L,"Darnytsya"),
                new StationSelectDto(10L,"Myrhorod"),
                new StationSelectDto(11L,"Poltava Kyivska"),
                new StationSelectDto(12L,"Kharkiv-Pas"),
                new StationSelectDto(13L,"Dniprobud 2"),
                new StationSelectDto(14L,"Nikopol"),
                new StationSelectDto(15L,"Trypillya Dniprovske"),
                new StationSelectDto(16L,"Melitopol"),
                new StationSelectDto(17L,"Novooleksiyivka"),
                new StationSelectDto(18L,"Henichesk"),
                new StationSelectDto(19L,"Synelnykove-1"),
                new StationSelectDto(20L,"Krasnopavlivka")
                );
        String expectedJson = objectMapper.writeValueAsString(stationSelectDtoList);

        MvcResult content = mockMvc.perform(get("/api/station/select"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        Assert.assertEquals(expectedJson, resultJson);
    }
}