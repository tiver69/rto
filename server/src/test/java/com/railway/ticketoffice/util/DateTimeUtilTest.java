package com.railway.ticketoffice.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    @Test
    public void shouldReturnCorrectDifference(){
        LocalTime localTime = LocalTime.of(19,30);
        LocalTime localTime2 = LocalTime.of(7,25);
        LocalTime result = DateTimeUtil.getDuration(localTime, localTime2);
        LocalTime expectedResult = LocalTime.of(11,55);

        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void shouldReturnCorrectDifference2(){
        LocalTime localTime = LocalTime.of(7,25);
        LocalTime localTime2 = LocalTime.of(19,30);
        LocalTime result = DateTimeUtil.getDuration(localTime, localTime2);
        LocalTime expectedResult = LocalTime.of(12,5);

        Assert.assertEquals(result, expectedResult);
    }
}
