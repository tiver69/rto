package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrainCoachValidatorTest {

    @Mock
    private TrainCoachRepository trainCoachRepository;

    @InjectMocks
    private TrainCoachValidator trainCoachValidator;

    private static Long NOT_EXISTING_ID = 12345L;
    private static Long TRAIN_ID = 12345L;

    @Test(expected = DataNotFoundException.class)
    public void shouldReturnExceptionWithNotExistingPassengerId() {
        when(trainCoachRepository.findByIdAndTrainId(NOT_EXISTING_ID, TRAIN_ID)).thenReturn(Optional.empty());

        try {
            trainCoachValidator.validateExistenceCoachInTrain(NOT_EXISTING_ID, TRAIN_ID);
        } catch (DataNotFoundException ex) {
            String message = ex.getMessage();
            Assert.assertEquals(String.format(TrainCoachValidator.EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT, NOT_EXISTING_ID, TRAIN_ID),
                    message);
            throw ex;
        }
    }
}