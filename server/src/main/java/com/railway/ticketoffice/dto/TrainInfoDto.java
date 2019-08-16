package com.railway.ticketoffice.dto;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TrainInfoDto {
    private Long id;
    private String firstStationName;
    private String lastStationName;

    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalTime duration;

    public TrainInfoDto(Long id, LocalTime departureTime, LocalTime arrivalTime) {
        this.id = id;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
