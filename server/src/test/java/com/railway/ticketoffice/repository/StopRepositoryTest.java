package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.domain.WeekDay;
import com.railway.ticketoffice.dto.request.train.TrainInfoDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StopRepositoryTest {

    private static long DB_STATION1_ID = 2L;
    private static long DB_STATION2_ID = 1L;
    private static long DB_STATION3_ID = 11L;
    private static long DB_TRAIN_ID = 732L;
    private static long DB_TRAIN_COACH_ID = 1L;

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void shouldReturnExpectedCountOfStops() {
        int expected = 40;

        int result = stopRepository.findAll().size();
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnExpectedTrainInfoDtoListWithDBData() {
        List<TrainInfoDto> expected = Arrays.asList(
                TrainInfoDto.builder()
                        .id(72L).coachNumber(8).firstStationName("Kyiv-Pasazhyrsky").lastStationName("Zaporizhzhya 1")
                        .arrivalTime(LocalTime.of(7, 49)).departureTime(LocalTime.of(20, 12))
                        .build(),
                TrainInfoDto.builder()
                        .id(732L).coachNumber(9).firstStationName("Kyiv-Pasazhyrsky").lastStationName("Zaporizhzhya 1")
                        .arrivalTime(LocalTime.of(14, 35)).departureTime(LocalTime.of(7, 7))
                        .build());

        List<TrainInfoDto> result = stopRepository
                .findAllTrainsByDirectionAndWeekDay(DB_STATION1_ID, DB_STATION2_ID, WeekDay.MON);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnEmptyTrainInfoDtoWithDBData() {
        List<TrainInfoDto> result = stopRepository
                .findAllTrainsByDirectionAndWeekDay(DB_STATION2_ID, DB_STATION3_ID, WeekDay.MON);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnExpectedCountOfStopsBetweenStations() {
        int expected = 8;

        List<Stop> result = stopRepository.
                getAllStopsInDirectionAndTrainId(DB_STATION1_ID, DB_STATION2_ID, DB_TRAIN_ID);
        Assert.assertEquals(expected, result.size());
    }

    @Test
    public void shouldCountExpectedPriceForDirection() {
        Integer expected = 472;

        Optional<Integer> result = stopRepository
                .countPriceByDirectionAndTrainCoachId(DB_TRAIN_ID, DB_TRAIN_COACH_ID, DB_STATION1_ID, DB_STATION2_ID);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(expected, result.get());
    }
}