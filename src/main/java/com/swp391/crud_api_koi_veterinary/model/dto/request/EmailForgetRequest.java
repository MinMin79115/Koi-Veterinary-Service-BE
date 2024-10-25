package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailForgetRequest {
    private String email;
    private String subject;
    private String body;
}
