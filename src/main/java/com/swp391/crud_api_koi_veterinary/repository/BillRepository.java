package com.swp391.crud_api_koi_veterinary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391.crud_api_koi_veterinary.model.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    List<Bill> findByBooking_BookingId(int bookingId);
}