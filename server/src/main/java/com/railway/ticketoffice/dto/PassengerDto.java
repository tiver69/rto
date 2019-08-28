package com.railway.ticketoffice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PassengerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private Long totalTickets;
    private LocalDate lastActive;
    private boolean isEditMode;

    public PassengerDto(Long id, String firstName, String lastName, String login, Long totalTickets, LocalDate lastActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.totalTickets = totalTickets;
        this.lastActive = lastActive;
        this.isEditMode = false;
    }
}
