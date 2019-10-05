package com.railway.ticketoffice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer markup;

    @ElementCollection(targetClass = WeekDay.class)
    @CollectionTable(name = "train_frequency", joinColumns = @JoinColumn(name = "train_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "week_day")
    private Set<WeekDay> frequency;
}
