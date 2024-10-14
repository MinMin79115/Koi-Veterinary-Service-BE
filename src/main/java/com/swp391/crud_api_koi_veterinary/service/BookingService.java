package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingStatusUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;
import com.swp391.crud_api_koi_veterinary.model.entity.TimeSlot;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;

import java.util.List;

public interface BookingService {
    Booking createBooking(BookingRequest request, String username);
    List<ServicesDetail> getAvailableServices();
    List<TimeSlot> getAvailableTimeSlots();
    List<Veterinarian> getVeterinarian();

    //CRUD Booking
    List<Booking> getAllBooking();
    List<Booking> getBookingByUserId(int id);
    List<Booking> getBookingByVeterinarianId(int veterinarianId);
    Booking updateBookingStatus(BookingStatusUpdateRequest request, Integer bookingId);
    Booking deleteBooking(Integer bookingId);
}
