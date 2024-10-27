package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResetPasswordRequest {
    private String username;
    private String password;
}
