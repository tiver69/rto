package com.railway.ticketoffice.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainRepositoryTest {

    @Autowired
    private TrainRepository trainRepository;

    @Test
    public void shouldReturnExpectedCountOfTrains() {
        int expected = 6;

        int result = trainRepository.findAll().size();
        Assert.assertEquals(expected, result);
    }
}