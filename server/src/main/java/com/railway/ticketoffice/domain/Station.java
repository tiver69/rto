package com.railway.ticketoffice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_en")
    private String name;

}
