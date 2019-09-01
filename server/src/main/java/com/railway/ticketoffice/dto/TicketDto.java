package com.railway.ticketoffice.dto;

import com.railway.ticketoffice.util.DateTimeUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TicketDto {

    private Long id;
    private String departureStation;
    private String destinationStation;
    private String departureDateTime;
    private String arrivalDateTime;
    private Long trainId;
    private Integer coachNumber;
    private Integer place;
    private Integer price;

    public TicketDto(Long id, String departureStation, String destinationStation, LocalDate departureDate,
                     LocalTime departureTime, LocalDate arrivalDate, LocalTime arrivalTime, Long trainId, Integer coachNumber,
                     Integer place, Integer price) {
        this.id = id;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.departureDateTime = DateTimeUtil.
                localDateTimeToString(departureDate.atTime(departureTime));
        this.arrivalDateTime = DateTimeUtil.
                localDateTimeToString(arrivalDate.atTime(arrivalTime));
        this.trainId = trainId;
        this.coachNumber = coachNumber;
        this.place = place;
        this.price = price;
    }
}
