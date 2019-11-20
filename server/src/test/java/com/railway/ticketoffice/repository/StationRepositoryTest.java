package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Station;
import com.railway.ticketoffice.dto.StationSelectDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@DataJpaTest
public class StationRepositoryTest {

    private static final long DB_STATION_ID = 1L;
    private static final long NOT_DB_STATION_ID = 666L;

    @Autowired
    private StationRepository stationRepository;

    @Test
    public void shouldReturnExpectedStation() {
        Station expected = Station.builder().id(1L).name("Zaporizhzhya 1").build();

        Optional<Station> result = stationRepository.findById(DB_STATION_ID);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(expected, result.get());
    }

    @Test
    public void shouldReturnEmptyStation() {
        Optional<Station> result = stationRepository.findById(NOT_DB_STATION_ID);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnExpectedCountOfStations() {
        int expected = 20;

        List<Station> result = stationRepository.findAll();
        Assert.assertEquals(expected, result.size());
    }

    @Test
    public void shouldReturnExpectedAllStations() {
        List<StationSelectDto> result = stationRepository.findAllStationSelect();
        List<Station> result2 = stationRepository.findAll();
        Assert.assertEquals(
                result.stream().map(StationSelectDto::getLabel).collect(Collectors.toList()),
                result2.stream().map(Station::getName).collect(Collectors.toList()));
    }
}