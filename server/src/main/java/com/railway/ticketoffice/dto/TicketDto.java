package com.railway.ticketoffice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
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
    private boolean isActive;
}
