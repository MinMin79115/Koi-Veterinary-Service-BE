package com.swp391.crud_api_koi_veterinary.controller;

import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingStatusUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;
import com.swp391.crud_api_koi_veterinary.model.entity.TimeSlot;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingRequest request, Authentication authentication) {
        String username = authentication.getName();
        Booking booking = bookingService.createBooking(request, username);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServicesDetail>> getAvailableServices() {
        List<ServicesDetail> services = bookingService.getAvailableServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/timeslots")
    public ResponseEntity<List<TimeSlot>> getAvailableTimeSlots() {
        List<TimeSlot> timeSlots = bookingService.getAvailableTimeSlots();
        return ResponseEntity.ok(timeSlots);
    }

    @GetMapping("/veterinarians")
    public ResponseEntity<List<Veterinarian>> getVeterinarian() {
        List<Veterinarian> veterinarians = bookingService.getVeterinarian();
        return ResponseEntity.ok(veterinarians);
    }
//Get All booking API
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBooking() {
        List<Booking> bookings = bookingService.getAllBooking();
        return ResponseEntity.ok(bookings);
    }
//Update booking API
    @PutMapping("/{bookingId}")
    public Booking updateBooking(@RequestBody BookingStatusUpdateRequest request, @PathVariable Integer bookingId){
        return bookingService.updateBookingStatus(request, bookingId);
    }
//Cancelled booking API
    @PutMapping("delete/{bookingId}")
    public Booking deleteBooking(@PathVariable Integer bookingId){
        return bookingService.deleteBooking(bookingId);
    }
// Api để lấy danh sách booking theo userId
    @GetMapping("/user/{id}")
    public List<Booking> getBookingByUserId(@PathVariable int id) {
        return bookingService.getBookingByUserId(id);
    }

// Api để lấy danh sách booking theo veterinarianId
    @GetMapping("/veterinarian/{veterinarianId}")
    public List<Booking> getBookingByVeterinarianId(@PathVariable int veterinarianId) {
        return bookingService.getBookingByVeterinarianId(veterinarianId);
    }
    
}