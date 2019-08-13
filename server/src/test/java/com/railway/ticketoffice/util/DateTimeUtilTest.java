package com.railway.ticketoffice.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class DateTimeUtilTest {

    @Test
    public void shouldReturnNextDayFromDepartureDate() {
        LocalDate departureDate = LocalDate.of(2019, 8, 13);
        LocalTime departureTime = LocalTime.of(19, 0);
        LocalTime arrivalTime = LocalTime.of(8, 0);

        LocalDate result = DateTimeUtil.getArrivalDate(departureDate, departureTime, arrivalTime);
        LocalDate expectedResult = LocalDate.of(2019, 8, 14);

        Assert.assertTrue(result.isEqual(expectedResult));
    }

    @Test
    public void shouldReturnSameDayAsDepartureDate() {
        LocalDate departureDate = LocalDate.of(2019, 8, 13);
        LocalTime departureTime = LocalTime.of(19, 0);
        LocalTime arrivalTime = LocalTime.of(21, 0);

        LocalDate result = DateTimeUtil.getArrivalDate(departureDate, departureTime, arrivalTime);
        LocalDate expectedResult = LocalDate.of(2019, 8, 13);

        Assert.assertTrue(result.isEqual(expectedResult));
    }

    @Test
    public void shouldReturnTrueIfFutureDate() {
        LocalDateTime dateTime = LocalDateTime.now().plusHours(3);

        boolean result = DateTimeUtil.isFuture(dateTime);
        Assert.assertTrue(result);
    }

    @Test
    public void shouldReturnFalseIfPastDate() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(3);

        boolean result = DateTimeUtil.isFuture(dateTime);
        Assert.assertFalse(result);
    }

}
