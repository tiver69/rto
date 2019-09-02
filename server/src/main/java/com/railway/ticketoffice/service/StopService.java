package com.railway.ticketoffice.service;

import com.railway.ticketoffice.domain.Stop;
import com.railway.ticketoffice.repository.StopRepository;
import com.railway.ticketoffice.util.DateTimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class StopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StopService.class);

    @Autowired
    private StopRepository stopRepository;

    public long countTrainDirectionDurationInMinutes(Long trainId, Long departureStationId, Long destinationStationId) {
        List<Stop> stops = stopRepository.
                getAllStopsInDirectionAndTrainId(departureStationId, destinationStationId, trainId);
        int durationInMinutes = 0;

        for (int i = 0; i < stops.size() - 2; i++) {
            durationInMinutes += DateTimeUtil.getDurationInMinutes(
                    stops.get(i).getDeparture(), stops.get(i + 1).getDeparture());
        }
        durationInMinutes += DateTimeUtil.getDurationInMinutes(
                stops.get(stops.size() - 2).getDeparture(), stops.get(stops.size() - 1).getArrival());

        LOGGER.info("Duration request for train#{}, departureStation#{} - arrivalStation#{} - count {}",
                trainId, departureStationId, destinationStationId,durationInMinutes);
        return durationInMinutes;
    }

    public LocalDate countArrivalDateFromRootDuration(LocalDate departureDate, Long trainId, Long departureStationId, Long destinationStationId) {
        List<Stop> stops = stopRepository.
                getAllStopsInDirectionAndTrainId(departureStationId, destinationStationId, trainId);
        int durationInMinutes = 0;

        for (int i = 0; i < stops.size() - 2; i++) {
            durationInMinutes += DateTimeUtil.getDurationInMinutes(
                    stops.get(i).getDeparture(), stops.get(i + 1).getDeparture());
        }
        durationInMinutes += DateTimeUtil.getDurationInMinutes(
                stops.get(stops.size() - 2).getDeparture(), stops.get(stops.size() - 1).getArrival());

        LocalDateTime test = LocalDateTime.of(departureDate, stops.get(0).getDeparture());
        test = test.plusMinutes(durationInMinutes);

        LOGGER.info("Arrival date request for {} at train#{}, departureStation#{} - arrivalStation#{} - count {}",
               departureDate, trainId, departureStationId, destinationStationId, test.toLocalDate());
        return test.toLocalDate();
    }
}
