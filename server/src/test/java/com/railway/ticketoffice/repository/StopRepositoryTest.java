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
public class StopRepositoryTest {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void findAll() {
        System.out.println(stopRepository.findAll());
    }

    @Test
    public void findByTrainIdAndStationId() {
        System.out.println(stopRepository.findByTrainIdAndStationId(72L, 1L));
    }

    @Test
    public void findFirstByTrainIdOrderByOrderDesc(){
        System.out.println(stopRepository.findFirstByTrainIdOrderByOrderDesc(72L));
    }

    @Test
    public void test() {
        System.out.println(stopRepository.
                findAllTrainsByDirection( 2L, 1L));
    }
}