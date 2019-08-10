package com.railway.ticketoffice.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "passengers")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String login;
    private String password;
}
