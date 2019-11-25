package com.railway.ticketoffice.util;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalTime;

public class DateTimeUtilTest {

    @Test
    public void shouldReturnCorrectDifference() {
        long expectedResult = 11 * 60 + 55;

        LocalTime localTime = LocalTime.of(19, 30);
        LocalTime localTime2 = LocalTime.of(7, 25);
        long result = DateTimeUtil.getDurationInMinutes(localTime, localTime2);

        Assert.assertEquals(result, expectedResult);
    }

    @Test
    public void shouldReturnCorrectDifference2() {
        long expectedResult = 12 * 60 + 5;

        LocalTime localTime = LocalTime.of(7, 25);
        LocalTime localTime2 = LocalTime.of(19, 30);
        long result = DateTimeUtil.getDurationInMinutes(localTime, localTime2);

        Assert.assertEquals(result, expectedResult);
    }
}
