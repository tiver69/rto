package com.railway.ticketoffice.domain;

public enum WeekDay {
    SUN(7), MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6);

    private int value;

    public int getValue() {
        return this.value;
    }

    WeekDay(int value) {
        this.value = value;
    }
}
