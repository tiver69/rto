package com.railway.ticketoffice.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Ignore
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void findAll() {
        System.out.println(ticketRepository.findAll());
    }

    @Test
    public void findActivePageByPassengerId() {
        System.out.println(ticketRepository.findActivePageByPassengerId(1L, LocalDate.now(), PageRequest.of(0, 5))
                .getContent());
    }

    @Test
    public void countAllBookedPlacesByDateAndTrainId() {
        System.out.println(ticketRepository.countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(732L, LocalDate.of(2019, 8, 31)));
    }

    @Test
    public void findAllBookedPlacesInCoachByNumberAndTrainIdAndDepartureDate() {
        System.out.println(ticketRepository.findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(732L, LocalDate.of(2019, 8, 31), 5));
    }
}
