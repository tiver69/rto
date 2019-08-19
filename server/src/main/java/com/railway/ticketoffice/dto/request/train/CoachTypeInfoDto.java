package com.railway.ticketoffice.dto.request.train;

import lombok.Data;

@Data
public class CoachTypeInfoDto {
    String coachTypeName;
    Long count;
    Long availablePlaces;
    Long totalPlaces;

    public CoachTypeInfoDto(String coachTypeName, Long count, Long totalPlaces) {
        this.coachTypeName = coachTypeName;
        this.count = count;
        this.totalPlaces = totalPlaces;
    }

    public CoachTypeInfoDto(String coachTypeName, Long availablePlaces) {
        this.coachTypeName = coachTypeName;
        this.availablePlaces = availablePlaces;
    }
}
