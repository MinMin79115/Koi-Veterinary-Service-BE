package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.config.VNPayConfig;
import com.swp391.crud_api_koi_veterinary.model.dto.request.PaymentDTO;
import com.swp391.crud_api_koi_veterinary.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
        private final VNPayConfig vnpayConfig;

    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request, long amount, int bookingId) {
        Map<String, String> vnpParams = vnpayConfig.getVNPayConfig();
        vnpParams.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
        vnpParams.put("vnp_Amount", String.valueOf(amount * 100));
        vnpParams.put("vnp_TxnRef", String.valueOf(bookingId));

        String paymentUrl = vnpayConfig.getVnp_PayUrl() + "?" + VNPayUtil.getPaymentURL(vnpParams, true);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnpayConfig.getSecretKey(), VNPayUtil.getPaymentURL(vnpParams, false));
        paymentUrl += "&vnp_SecureHash=" + vnpSecureHash;

        return PaymentDTO.VNPayResponse.builder()
                .code("00")
                .message("success")
                .paymentUrl(paymentUrl)
                .build();
    }
    }

