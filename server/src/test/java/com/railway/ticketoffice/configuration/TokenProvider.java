package com.railway.ticketoffice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.security.JwtLoginSuccessResponse;
import com.railway.ticketoffice.dto.security.LoginRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.railway.ticketoffice.security.Constants.TOKEN_PREFIX;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class TokenProvider {

    public static String NOT_VALID_TOKEN = TOKEN_PREFIX + "not_valid_token";
    public static Passenger PASSENGER_FOR_VALID_TOKEN = Passenger.builder()
            .id(1L)
            .login("tiver69")
            .password("$2a$10$iRXmGGxSIYcu4lMDZnCN6eNjgNU7YVrNdl9A4FRgD8Y1v3/pF51aK")
            .firstName("Aleksandra")
            .lastName("Kibko")
            .build();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String getValidPassengerToken(MockMvc mockMvc) throws Exception {
        LoginRequest loginRequest = new LoginRequest("tiver69", "pass17");
        String passengerJson = objectMapper.writeValueAsString(loginRequest);

        MvcResult content = mockMvc.perform(post("/api/passenger/login")
                .contentType(APPLICATION_JSON_UTF8)
                .content(passengerJson))
                .andReturn();
        String resultJson = content.getResponse().getContentAsString();
        JwtLoginSuccessResponse obj= objectMapper.readValue(resultJson, JwtLoginSuccessResponse.class);
        return obj.getToken();
    }
}
