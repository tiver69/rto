package com.railway.ticketoffice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate getArrivalDate(LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime))
            return departureDate.plusDays(1L);
        return departureDate;
    }

    public static boolean isFuture(LocalDateTime dateTime){
        return dateTime.isAfter(LocalDateTime.now());
    }

    public static LocalDate parseString(String stringDate) {
        return LocalDate.parse(stringDate, DATE_FORMATTER);
    }

}
