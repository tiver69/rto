package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.TrainCoach;
import com.railway.ticketoffice.dto.request.coach.CoachInfoDto;
import com.railway.ticketoffice.dto.request.train.CoachTypeInfoDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainCoachRepositoryTest {

    private static long DB_TRAIN_ID = 72L;
    private static long DB_COACH_ID = 19L;
    private static int DB_COACH_NUMBER = 1;
    private static int NOT_EXISTING_COACH_NUMBER = 666;

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Test
    public void shouldReturnExpectedCountOfTickets() {
        int expectedCount = 49;
        int resultTicketCount = trainCoachRepository.findAll().size();
        Assert.assertEquals(expectedCount, resultTicketCount);
    }

    @Test
    public void shouldReturnExpectedTrainCoachItem() {
        Optional<TrainCoach> expected = trainCoachRepository.findById(DB_COACH_ID);

        Optional<TrainCoach> result = trainCoachRepository.findByIdAndTrainId(DB_COACH_ID, DB_TRAIN_ID);
        Assert.assertTrue(result.isPresent());
        Assert.assertTrue(expected.isPresent());
        Assert.assertEquals(expected.get(), result.get());
    }

    @Test
    public void shouldReturnEmptyTrainCoachItem() {
        Optional<TrainCoach> result = trainCoachRepository.
                findByIdAndTrainId(DB_COACH_ID + 10L, DB_TRAIN_ID);
        Assert.assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnExpectedCoachTypeInfoDtoList() {
        List<CoachTypeInfoDto> expected = Arrays.asList(
                new CoachTypeInfoDto("Compartment", 5L, 180L),
                new CoachTypeInfoDto("Berth", 3L, 162L));

        List<CoachTypeInfoDto> result = trainCoachRepository.countAllCoachTypesByTrainId(DB_TRAIN_ID);
        Assert.assertEquals(expected, result);
    }

    @Test
    public void shouldReturnExpectedCoachInfoDto() {
        CoachInfoDto expected = new CoachInfoDto(22L, 3L, "Compartment", 1, 36);

        Optional<CoachInfoDto> result = trainCoachRepository.findCoachByNumberAndTrainId(DB_TRAIN_ID, DB_COACH_NUMBER);
        Assert.assertTrue(result.isPresent());
        Assert.assertEquals(expected, result.get());
    }

    @Test
    public void shouldReturnEmptyCoachInfoDto() {
        Optional<CoachInfoDto> result = trainCoachRepository
                .findCoachByNumberAndTrainId(DB_TRAIN_ID, NOT_EXISTING_COACH_NUMBER);
        Assert.assertFalse(result.isPresent());
    }
}
