package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.dto.TicketDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketRepositoryTest {

    private static long DB_PASSENGER_ID = 1L;
    private static long DB_TRAIN_ID = 732L;
    private static int DB_COACH_NUMBER = 5;
    private static LocalDate DB_DATE = LocalDate.of(2019, 8, 31);
    private static LocalDate NOT_DB_DATE = LocalDate.of(2019, 8, 30);

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    public void shouldReturnExpectedCountOfTickets() {
        int expected = 14;

        int result = ticketRepository.findAll().size();
        Assert.assertEquals(expected, result);
    }

    @Test
    @Ignore
    public void findActivePageByPassengerId() {
        Page<TicketDto> result = ticketRepository.findPageByPassengerIdAndActiveStatus(DB_PASSENGER_ID, DB_DATE,
                true, PageRequest.of(1, 5));
        Assert.assertEquals(2, result.getTotalPages());
        Assert.assertEquals(1, result.getContent().size());
    }

    @Test
    public void shouldReturnExpectedCoachTypeInfoDtoList() {
        List<CoachTypeInfoDto> expected = Arrays.asList(
                new CoachTypeInfoDto("Seating first class", 2L),
                new CoachTypeInfoDto("Seating second class", 1L));

        List<CoachTypeInfoDto> result = ticketRepository.
                countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(DB_TRAIN_ID, DB_DATE);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnEmptyCoachTypeInfoDtoListWithNotExistingDBDate() {
        List<CoachTypeInfoDto> result = ticketRepository.
                countBookedPlacesInCoachTypeByTrainIdAndDepartureDate(DB_TRAIN_ID, NOT_DB_DATE);
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void shouldReturnExpectedPlaceListForDBData() {
        List<Integer> expected = Arrays.asList(17, 18);

        List<Integer> result = ticketRepository.
                findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(DB_TRAIN_ID, DB_DATE, DB_COACH_NUMBER);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnEmptyPlaceListForDBData() {
        List<Integer> result = ticketRepository.
                findAllBookedPlacesByCoachNumberAndTrainIdAndDepartureDate(DB_TRAIN_ID, NOT_DB_DATE, DB_COACH_NUMBER);
        Assert.assertEquals(0, result.size());
    }
}
