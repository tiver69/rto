package com.railway.ticketoffice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StationSelectDto {
    private Long value;
    private String label;
}
