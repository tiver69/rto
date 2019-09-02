package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.WeekDay;
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
//@Ignore
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
    public void findAllTrainsByDirection() {
        System.out.println(stopRepository.
                findAllTrainsByDirectionAndWeekDay(2L, 1L, WeekDay.SUN));
    }

    @Test
    public void getAllStopsInDirectionAndTrain() {
        System.out.println(stopRepository.
                getAllStopsInDirectionAndTrainId(1L, 2L, 73L));
    }

    @Test
    public void countPriceByDirectionAndTrainId() {
        System.out.println(stopRepository.countPriceByDirectionAndTrainCoachId(7772L, 20L, 4L, 1L));
    }
}