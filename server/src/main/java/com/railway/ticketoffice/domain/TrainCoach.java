package com.railway.ticketoffice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainCoach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "train_id", referencedColumnName = "id", nullable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "coach_type_id", referencedColumnName = "id", nullable = false)
    private CoachType coachType;

    private Integer number;
}
