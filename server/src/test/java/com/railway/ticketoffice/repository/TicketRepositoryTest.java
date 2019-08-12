package com.railway.ticketoffice.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
public class TicketRepositoryTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void findAll() {
        System.out.println(ticketRepository.findAll());
    }

    @Test
    public void findAllByPassengerId() {
        System.out.println(ticketRepository.findAllByPassengerId(1L));
    }
}