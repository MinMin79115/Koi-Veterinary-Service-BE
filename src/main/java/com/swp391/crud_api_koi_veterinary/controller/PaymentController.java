package com.swp391.crud_api_koi_veterinary.controller;


import com.swp391.crud_api_koi_veterinary.model.dto.request.PaymentDTO;
import com.swp391.crud_api_koi_veterinary.model.dto.response.ResponseObject;
import com.swp391.crud_api_koi_veterinary.service.BillService;
import com.swp391.crud_api_koi_veterinary.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
        private final PaymentService paymentService;
        private final BillService billService;

    @GetMapping("/vnpay")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request, @RequestParam long amount, @RequestParam int bookingId) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, amount, bookingId));
    }

    @GetMapping("/vnpayback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            int bookingId = Integer.parseInt(request.getParameter("vnp_TxnRef"));
            BigDecimal amount = new BigDecimal(request.getParameter("vnp_Amount")).divide(new BigDecimal(100));
            billService.createBill(bookingId, amount, "VNPay");
            return new ResponseObject<>(HttpStatus.OK, "Success", new PaymentDTO.VNPayResponse("00", "Success", ""));
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
    }

