package com.railway.ticketoffice.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "passenger_id", referencedColumnName = "id", nullable = false)
    private Passenger passenger;
    @ManyToOne
    @JoinColumn(name = "departure_station_id", referencedColumnName = "id", nullable = false)
    private Station departureStation;
    @ManyToOne
    @JoinColumn(name = "destination_station_id", referencedColumnName = "id", nullable = false)
    private Station destinationStation;
    private LocalDate departureDate;
    @ManyToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id", nullable = false)
    private TrainCoach trainCoach;
    private Integer place;
    private Integer price;
}
