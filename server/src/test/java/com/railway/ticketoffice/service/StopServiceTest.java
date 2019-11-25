package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.repository.StopRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StopServiceTest {

    @Mock
    private StopRepository stopRepository;

    @InjectMocks
    private StopService stopService;

    private static Long VALID_TRAIN_ID = 72L;
    private static Long VALID_DEPARTURE_STATION_ID = 1L;
    private static Long VALID_DESTINATION_STATION_ID = 2L;

    @Test
    public void shouldCountCorrectDurationWithValidParams() {
        long expectedDuration = 390;
        List<Stop> stops = Arrays.asList(
                Stop.builder().arrival(LocalTime.of(22, 30)).departure(LocalTime.of(23, 0)).build(),
                Stop.builder().arrival(LocalTime.of(4, 30)).departure(LocalTime.of(5, 0)).build(),
                Stop.builder().arrival(LocalTime.of(5, 30)).departure(LocalTime.of(6, 0)).build());

        when(stopRepository.getAllStopsInDirectionAndTrainId(VALID_DEPARTURE_STATION_ID, VALID_DESTINATION_STATION_ID,
                VALID_TRAIN_ID)).thenReturn(stops);
        long result = stopService.countTrainDirectionDurationInMinutes(VALID_TRAIN_ID, VALID_DEPARTURE_STATION_ID,
                VALID_DESTINATION_STATION_ID);

        Assert.assertEquals(expectedDuration, result);
    }
}
