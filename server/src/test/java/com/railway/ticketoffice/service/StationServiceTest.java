package com.railway.ticketoffice.service;

import com.railway.ticketoffice.dto.StationSelectDto;
import com.railway.ticketoffice.repository.StationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StationServiceTest {

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private StationService stationService;

    @Test
    public void should() {
        List<StationSelectDto> stationSelectDtos = Arrays.asList(
                new StationSelectDto(0L, "Label0"),
                new StationSelectDto(1L, "Label1"));
        when(stationRepository.findAllStationSelect()).thenReturn(stationSelectDtos);

        List<StationSelectDto> result = stationService.findAllForSelect();
        verify(stationRepository).findAllStationSelect();
    }
}
