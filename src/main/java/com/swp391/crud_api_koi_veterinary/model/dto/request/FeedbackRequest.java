package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.Data;

@Data
public class FeedbackRequest {
    private int bookingId;
    private int rating;
    private String feedback;
} 