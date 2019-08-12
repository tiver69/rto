package com.railway.ticketoffice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeUtil {

    public static LocalDate getArrivalDate(LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime))
            return departureDate.plusDays(1L);
        return departureDate;
    }

    public static boolean isFuture(LocalDateTime dateTime){
        return dateTime.isAfter(LocalDateTime.now());
    }

}
