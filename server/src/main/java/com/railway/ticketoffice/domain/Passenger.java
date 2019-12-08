package com.railway.ticketoffice.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Data
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Passenger extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastName;
    private String firstName;
    private String login;
    private String password;

    @Transient
    private String confirmPassword;

    @Transient
    @JsonIgnore
    private String username;

    @Override
    public String getUsername() {
        return login;
    }
}
