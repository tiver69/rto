package com.railway.ticketoffice.dto;

import lombok.Data;

@Data
public class StationSelectDto {
    private Long value;
    private String label;

    public StationSelectDto(Long value, String label) {
        this.value = value;
        this.label = label;
    }
}
