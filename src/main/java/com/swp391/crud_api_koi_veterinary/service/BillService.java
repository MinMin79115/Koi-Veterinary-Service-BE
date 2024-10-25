package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.enums.PaymentStatus;
import com.swp391.crud_api_koi_veterinary.model.entity.Bill;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.repository.BillRepository;
import com.swp391.crud_api_koi_veterinary.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;
    private final BookingRepository bookingRepository;

    public Bill createBill(int bookingId, BigDecimal amount, String paymentMethod) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        Bill bill = new Bill();
        bill.setBooking(booking);
        bill.setAmount(amount);
        bill.setPaymentStatus(PaymentStatus.PAID);
        bill.setPaymentMethod(paymentMethod);
        bill.setPaymentDate(LocalDateTime.now());

        return billRepository.save(bill);
    }

//lấy bill theo booking
    public List<Bill> getAllBill() {
        return billRepository.findAll();
    }
}
