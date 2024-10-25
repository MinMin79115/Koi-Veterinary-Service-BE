package com.swp391.crud_api_koi_veterinary.controller;


import com.swp391.crud_api_koi_veterinary.model.dto.request.PaymentDTO;
import com.swp391.crud_api_koi_veterinary.model.dto.response.ResponseObject;
import com.swp391.crud_api_koi_veterinary.model.entity.Bill;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.service.BillService;
import com.swp391.crud_api_koi_veterinary.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
//API lấy bill theo bookingId
    @GetMapping("/{bookingId}")
    public List<Bill> getServiceById(@PathVariable int bookingId) {
        return billService.getBillByBookingId(bookingId);
    }
}

