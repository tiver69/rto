package com.railway.ticketoffice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate getArrivalDate(LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime))
            return departureDate.plusDays(1L);
        return departureDate;
    }

    public static boolean isFuture(LocalDateTime dateTime){
        return dateTime.isAfter(LocalDateTime.now());
    }

    public static LocalDate parseString(String stringDate) throws DateTimeParseException {
        return LocalDate.parse(stringDate, DATE_FORMATTER);
    }

}
