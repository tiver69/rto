package com.railway.ticketoffice.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@Builder
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


    public Ticket(Long id, Passenger passenger, Station departureStation, Station destinationStation, LocalDate departureDate, TrainCoach trainCoach, Integer place, Integer price) {
        this.id = id;
        this.passenger = passenger;
        this.departureStation = departureStation;
        this.destinationStation = destinationStation;
        this.departureDate = departureDate;
        this.trainCoach = trainCoach;
        this.place = place;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", passenger=" + passenger +
                ", departureStation=" + departureStation +
                ", destinationStation=" + destinationStation +
                ", departureDate=" + departureDate +
                ", trainCoach=" + trainCoach +
                ", place=" + place +
                ", price=" + price +
                "}\n";
    }
}
