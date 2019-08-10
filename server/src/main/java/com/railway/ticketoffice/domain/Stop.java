package com.railway.ticketoffice.domain;

import com.railway.ticketoffice.domain.key.StopKey;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "train_stations")
@Data
public class Stop {
    @EmbeddedId
    private StopKey id;

    @ManyToOne
    @MapsId("train_id")
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    private Train train;

    @ManyToOne
    @MapsId("station_id")
    @JoinColumn(name = "station_id", referencedColumnName = "id", nullable = false)
    private Station station;

    private Integer order;
    private Integer prise;
    private LocalTime arrival;
    private LocalTime departure;

}
