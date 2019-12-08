package com.railway.ticketoffice.repository;

import com.railway.ticketoffice.domain.Passenger;
import com.railway.ticketoffice.dto.PassengerDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class PassengerRepositoryTest {

    @Autowired
    private PassengerRepository passengerRepository;

    private static final String DB_LOGIN = "tiver69";
    private static final String DB_LOGIN2 = "shipper1232";
    private static final long DB_ID = 1L;

    @Test
    public void shouldReturnSamePassenger() {
        Optional<Passenger> idResult = passengerRepository.findById(DB_ID);
        Optional<Passenger> loginResult = passengerRepository.findByLogin(DB_LOGIN);
        Assert.assertEquals(idResult, loginResult);
    }

    @Test
    public void shouldReturnExpectedCountOfPassengers() {
        int expected = 4;

        int resultTicketCount = passengerRepository.findAll().size();
        Assert.assertEquals(expected, resultTicketCount);
    }

    @Test
    public void findPagePassengersInfo() {
        Page<PassengerDto> result = passengerRepository.findPagePassengersInfo(PageRequest.of(0, 5));
        Assert.assertEquals(1, result.getTotalPages());
        Assert.assertEquals(4, result.getContent().size());
    }

    @Test
    @Rollback
    public void shouldSuccessfullyUpdatePassengerWithValidUpdateData() {
        Passenger updatePassenger = Passenger.builder()
                .id(DB_ID)
                .firstName("Aleksandra")
                .lastName("Kibko")
                .login("tiver6969")
                .password("$2a$10$iRXmGGxSIYcu4lMDZnCN6eNjgNU7YVrNdl9A4FRgD8Y1v3/pF51aK")
                .build();

        int result = passengerRepository.update(updatePassenger.getId(), updatePassenger.getFirstName(),
                updatePassenger.getLastName(), updatePassenger.getLogin());
        Assert.assertEquals(1, result);

        Optional<Passenger> updatedResult = passengerRepository.findById(DB_ID);
        Assert.assertTrue(updatedResult.isPresent());
        Assert.assertEquals(updatePassenger, updatedResult.get());
    }

    @Test(expected = DataIntegrityViolationException.class)
    @Rollback
    public void shouldThrowExceptionWhileUpdateWithExistingEmail() {
        Passenger updatePassenger = Passenger.builder()
                .id(DB_ID)
                .firstName("Aleksandra")
                .lastName("Kibko")
                .login(DB_LOGIN2)
                .build();

        int result = passengerRepository.update(updatePassenger.getId(), updatePassenger.getFirstName(),
                updatePassenger.getLastName(), updatePassenger.getLogin());
    }
}