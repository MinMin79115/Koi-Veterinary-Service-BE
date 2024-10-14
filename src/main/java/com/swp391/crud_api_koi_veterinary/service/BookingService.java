package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;
import com.swp391.crud_api_koi_veterinary.model.entity.TimeSlot;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest request, String username);
    List<ServicesDetail> getAvailableServices();
    List<TimeSlot> getAvailableTimeSlots();
    List<String> getVeterinarian();
}