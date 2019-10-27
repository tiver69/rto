package com.railway.ticketoffice.validator;

import com.railway.ticketoffice.domain.Ticket;
import com.railway.ticketoffice.domain.TrainCoach;
import com.railway.ticketoffice.exception.type.DataNotFoundException;
import com.railway.ticketoffice.exception.type.DataValidationException;
import com.railway.ticketoffice.repository.TrainCoachRepository;
import com.railway.ticketoffice.util.interfaces.ExistenceCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class TicketValidator {

    @Autowired
    private TrainCoachRepository trainCoachRepository;

    @Autowired
    private TrainCoachValidator trainCoachValidator;

    @Autowired
    private PassengerValidator passengerValidator;

    @Autowired
    private StationValidator stationValidator;

    private static String PLACE_KEY = "place";
    private static String PLACE_MESSAGE = "Place#%d doesn't exist in coach#%d";

    public void validate(Ticket ticket) throws DataValidationException {
        HashMap<String, String> causeObject = new HashMap<>();

        executeCheck(causeObject, () -> {
            passengerValidator.validateExistence(ticket.getPassenger().getId());
        });

        executeCheck(causeObject, () -> {
            stationValidator.validateExistence(ticket.getDepartureStation().getId());
        });

        executeCheck(causeObject, () -> {
            stationValidator.validateExistence(ticket.getDestinationStation().getId());
        });

        executeCheck(causeObject, () -> {
            trainCoachValidator.validateExistenceCoachInTrain(ticket.getTrainCoach().getId(),
                    ticket.getTrainCoach().getTrain().getId());
        });

        Optional<TrainCoach> trainCoach = trainCoachRepository.findById(ticket.getTrainCoach().getId());
        if (ticket.getPlace() <= 0 || (
                trainCoach.isPresent() &&
                        trainCoach.get().getCoachType().getPlaces() < ticket.getPlace()))
            causeObject.put(PLACE_KEY, String.format(PLACE_MESSAGE, ticket.getTrainCoach().getId(), ticket.getTrainCoach().getId()));

        if (!causeObject.isEmpty()) throw new DataValidationException("Ticket is not valid!", causeObject);
    }


    private void executeCheck(HashMap<String, String> causeObject, ExistenceCheck existenceCheck) {
        try {
            existenceCheck.check();
        } catch (DataNotFoundException ex) {
            causeObject.put(ex.getKey(), ex.getMessage());
        }
    }
}
