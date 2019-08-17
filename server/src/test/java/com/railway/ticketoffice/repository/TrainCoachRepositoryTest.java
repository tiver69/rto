package com.railway.ticketoffice.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Ignore
public class TrainCoachRepositoryTest {

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Test
    public void findAll() {
        System.out.println(trainCoachRepository.findAll());
    }

    @Test
    public void findAllCoachTypesByTrainId(){
        System.out.println(trainCoachRepository.countAllCoachTypesByTrainId(72L));
    }
}