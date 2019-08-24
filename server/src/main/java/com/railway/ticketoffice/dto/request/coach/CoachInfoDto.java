package com.railway.ticketoffice.dto.request.coach;

import lombok.Data;

import java.util.List;

@Data
public class CoachInfoDto {
    private Long id;
    private Long typeId;
    private String name;
    private Integer number;
    private Integer availablePlaces;
    private Integer totalPlaces;
    private List<Integer> bookedPlaces;

    public CoachInfoDto(Integer number, Integer totalPlaces) {
        this.number = number;
        this.totalPlaces = totalPlaces;
    }

    public CoachInfoDto(Long id, Long typeId, String name, Integer number, Integer totalPlaces) {
        this.id = id;
        this.typeId = typeId;
        this.name = name;
        this.number = number;
        this.totalPlaces = totalPlaces;
    }


}
