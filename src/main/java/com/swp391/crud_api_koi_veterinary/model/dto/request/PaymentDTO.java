package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.*;

public abstract class PaymentDTO {
    @Builder
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VNPayResponse {
        public String code;
        public String message;
        public String paymentUrl;
    }
}
