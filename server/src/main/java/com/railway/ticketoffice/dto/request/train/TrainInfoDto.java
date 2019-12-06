package com.railway.ticketoffice.dto.request.train;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainInfoDto {
    private Long id;
    private Integer coachNumber;
    private String firstStationName;
    private String lastStationName;

    private LocalDate departureDate;
    private LocalDate arrivalDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private String duration;

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
