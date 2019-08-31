package com.railway.ticketoffice.dto.request.train;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

@Data
public class TrainInfoDto {
    private Long id;
    private Integer coachNumber;
    private String firstStationName;
    private String lastStationName;

    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private LocalTime duration;

    private List<CoachTypeInfoDto> coachTypeInfoList;

    public TrainInfoDto(Long id, Integer coachNumber, String firstStationName, String lastStationName,
                        LocalTime departureTime, LocalTime arrivalTime) {
        this.id = id;
        this.coachNumber = coachNumber;
        this.firstStationName = firstStationName;
        this.lastStationName = lastStationName;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
