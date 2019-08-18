package com.railway.ticketoffice.dto.request.coach;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class CoachInfoDto {
    private String name;
    private Integer number;
    private Integer availablePlaces;
    private Integer totalPlaces;
    private List<Integer> bookedPlaces;

    public CoachInfoDto(Integer number, Integer totalPlaces) {
        this.number = number;
        this.totalPlaces = totalPlaces;
    }

    public CoachInfoDto(String name, Integer number, Integer totalPlaces) {
        this.name = name;
        this.number = number;
        this.totalPlaces = totalPlaces;
    }


}
