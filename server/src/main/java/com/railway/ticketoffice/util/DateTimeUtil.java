package com.railway.ticketoffice.util;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {

    public final static String DURATION_FORMATTER = "%02d:%02d";
    public final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDate getArrivalDate(LocalDate departureDate, LocalTime departureTime, LocalTime arrivalTime) {
        if (arrivalTime.isBefore(departureTime))
            return departureDate.plusDays(1L);
        return departureDate;
    }

    public static boolean isFuture(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    public static LocalDate parseString(String stringDate) throws DateTimeParseException {
        return LocalDate.parse(stringDate, DATE_FORMATTER);
    }

    public static String localDateTimeToString(LocalDateTime date) throws DateTimeParseException {
        return date.format(DATE_TIME_FORMATTER);
    }

    public static long getDurationInMinutes(LocalTime lesserTime, LocalTime biggerTime) {
        LocalDateTime lesserDateTime = LocalDate.now().atTime(lesserTime);
        LocalDateTime biggerDateTime = LocalDate.now().atTime(biggerTime);
        if (lesserTime.isAfter(biggerTime)) {
            biggerDateTime = biggerDateTime.plusDays(1);
        }
        return Duration.between(lesserDateTime, biggerDateTime).toMinutes();
    }

    public static String formatDuration(long durationInMinutes) {
        return String.format(DURATION_FORMATTER, durationInMinutes / 60, durationInMinutes % 60);
    }
}
