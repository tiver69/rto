package com.railway.ticketoffice.dto;

import com.railway.ticketoffice.domain.Train;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TrainInfoDto {
    private Train train;
    private LocalTime departureTime;
    private LocalTime arrivalTime;

    public TrainInfoDto(Train train, LocalTime departureTime, LocalTime arrivalTime) {
        this.train = train;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }
}
