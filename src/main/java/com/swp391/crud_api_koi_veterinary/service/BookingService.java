package com.swp391.crud_api_koi_veterinary.service;

import java.util.List;

import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingStatusUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;

public interface BookingService {
    Booking createBooking(BookingRequest request, String username);

    List<ServicesDetail> getAvailableServices();

    List<VeterinarianTimeSlot> getAvailableTimeSlots();

    List<Veterinarian> getVeterinarian();

    // CRUD Booking
    List<Booking> getAllBooking();

    List<Booking> getBookingByUserId(int id);

    List<Booking> getBookingByVeterinarianId(int veterinarianId);

    Booking updateBookingStatus(BookingStatusUpdateRequest request, Integer bookingId);

    Booking deleteBooking(Integer bookingId);
}
