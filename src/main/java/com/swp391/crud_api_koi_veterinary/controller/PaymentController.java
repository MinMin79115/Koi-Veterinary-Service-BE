package com.swp391.crud_api_koi_veterinary.controller;
import com.swp391.crud_api_koi_veterinary.model.dto.request.PaymentDTO;
import com.swp391.crud_api_koi_veterinary.model.dto.response.ResponseObject;
import com.swp391.crud_api_koi_veterinary.model.entity.Bill;
import com.swp391.crud_api_koi_veterinary.service.BillService;
import com.swp391.crud_api_koi_veterinary.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Tag(name = "Payment", description = "Payment management APIs")
public class PaymentController {
    private final PaymentService paymentService;
    private final BillService billService;

    @GetMapping("/vnpay")
    @Operation(summary = "Create VNPay payment", description = "Creates a VNPay payment with the specified amount and booking ID")
    public ResponseObject<PaymentDTO.VNPayResponse> pay(HttpServletRequest request, @RequestParam long amount, @RequestParam int bookingId) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(request, amount, bookingId));
    }

    @GetMapping("/vnpayback")
    @Operation(summary = "VNPay callback handler", description = "Handles the callback from VNPay after payment processing and redirects to appropriate page")
    public RedirectView payCallbackHandler(HttpServletRequest request) {
        String status = request.getParameter("vnp_ResponseCode");
        if (status.equals("00")) {
            int bookingId = Integer.parseInt(request.getParameter("vnp_TxnRef"));
            BigDecimal amount = new BigDecimal(request.getParameter("vnp_Amount")).divide(new BigDecimal(100));
            billService.createBill(bookingId, amount, "VNPay");
            return new RedirectView("http://localhost:5173/success?bookingId=" + bookingId + "&amount=" + amount + "&paymentMethod=VNPay");
        } else {
            return new RedirectView("http://localhost:5173/booking-detail");
        }
    }
//API lấy bill theo bookingId
    @GetMapping
    public List<Bill> getAllBill() {
        return billService.getAllBill();
    }
}

