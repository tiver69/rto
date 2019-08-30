package com.railway.ticketoffice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CoachType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en")
    String name;

    private Integer places;
    private Integer markup;
}
