package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainCoachValidator {

    public static String KEY = "trainCoach";
    public static String EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT = "Coach#%d doesn't exist in train#%d";

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    public void validateExistenceCoachInTrain(Long trainCoachId, Long trainId) throws DataNotFoundException {
        trainCoachRepository.findByIdAndTrainId(trainCoachId, trainId).orElseThrow(
                () -> new DataNotFoundException(KEY, String.format(EXIST_COACH_IN_TRAIN_MESSAGE_FORMAT, trainCoachId, trainId)));
    }
}
