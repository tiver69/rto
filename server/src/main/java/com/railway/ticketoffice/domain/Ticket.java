package com.railway.ticketoffice.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private Passenger passenger;
//    private Station departureStation;
//    private Station destinationStation;
    private LocalDate departureDate;
//    private TrainCoach trainCoach;
    private Integer place;
    private Integer price;
}
