package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingRequest {
    private Integer servicesDetailId;
    private Integer slotId;
    private Integer veterinarianId;
    private LocalDate serviceTime;
}
