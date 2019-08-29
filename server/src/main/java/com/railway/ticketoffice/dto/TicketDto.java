package com.railway.ticketoffice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class TicketDto {

    private Long id;
    private String departureStation;
    private String destinationStation;
    private LocalDate departureDate;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    private String departureDateTime;
    private String arrivalDateTime;

    private Long trainId;
    private Integer coachNumber;
    private Integer place;
    private Integer price;

    private boolean isActive;

    public TicketDto(Long id, String departureStation, String destinationStation, LocalDate departureDate, LocalTime departureTime,  LocalTime arrivalTime, Long trainId, Integer coachNumber, Integer place, Integer price) {
        this.id = id;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.trainId = trainId;
        this.coachNumber = coachNumber;
        this.place = place;
        this.price = price;
    }
}
