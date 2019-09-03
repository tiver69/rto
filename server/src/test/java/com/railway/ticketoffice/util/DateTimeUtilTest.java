package com.railway.ticketoffice.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class DateTimeUtilTest {

    @Test
    public void shouldReturnCorrectDifference() {
        LocalTime localTime = LocalTime.of(19, 30);
        LocalTime localTime2 = LocalTime.of(7, 25);
        long result = DateTimeUtil.getDurationInMinutes(localTime, localTime2);
        long expectedResult = 11 * 60 + 55;

        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void shouldReturnCorrectDifference2() {
        LocalTime localTime = LocalTime.of(7, 25);
        LocalTime localTime2 = LocalTime.of(19, 30);
        long result = DateTimeUtil.getDurationInMinutes(localTime, localTime2);
        long expectedResult = 12 * 60 + 5;

        Assert.assertEquals(result, expectedResult);
    }
}
