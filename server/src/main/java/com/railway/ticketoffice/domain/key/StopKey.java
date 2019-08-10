package com.railway.ticketoffice.domain.key;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class StopKey implements Serializable {

    @Column(name = "train_id")
    Long trainId;

    @Column(name = "station_id")
    Long stationId;
}
