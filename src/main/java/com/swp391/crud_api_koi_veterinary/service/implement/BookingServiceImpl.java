package com.swp391.crud_api_koi_veterinary.service.implement;

import com.swp391.crud_api_koi_veterinary.enums.BookingStatus;
import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingStatusUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.*;
import com.swp391.crud_api_koi_veterinary.repository.*;
import com.swp391.crud_api_koi_veterinary.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ServicesDetailRepository servicesDetailRepository;
    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianTimeSlotRepository veterinarianTimeSlotRepository;

    @Override
    public Booking createBooking(BookingRequest request, String username) {
        UserAccount user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServicesDetail servicesDetail = servicesDetailRepository.findById(request.getServicesDetailId())
                .orElseThrow(() -> new RuntimeException("Services not found"));

        VeterinarianTimeSlot timeSlot = request.getSlotId() != null ?
               veterinarianTimeSlotRepository.findById(request.getSlotId())
                       .orElseThrow(() -> new RuntimeException("Slot not found")) : null;

        Veterinarian veterinarian = request.getVeterinarianId() != null ?
                veterinarianRepository.findById(request.getVeterinarianId())
                        .orElseThrow(() -> new RuntimeException("Veterinarian not found")) : null;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setServicesDetail(servicesDetail);
        if (veterinarian != null) {
            booking.setVeterinarian(veterinarian);
        }
        if (timeSlot != null) {
            // Check if the timeSlot is already UNAVAILABLE
            if (timeSlot.getSlotStatus() == SlotStatus.UNAVAILABLE) {
                throw new RuntimeException("Slot is not available now");
            }

            booking.setSlot(timeSlot);
            timeSlot.setSlotStatus(SlotStatus.UNAVAILABLE); // Set slot status to UNAVAILABLE
            veterinarianTimeSlotRepository.save(timeSlot); // Save the updated timeSlot
        }
        booking.setBookingTime(LocalDateTime.now());
        if (request.getServiceTime() !=null) {
            booking.setServiceTime(request.getServiceTime());
        }
        booking.setStatus(BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    @Override
    public List<ServicesDetail> getAvailableServices() {
        return servicesDetailRepository.findAllWithDetails();
    }

    @Override
    public List<VeterinarianTimeSlot> getAvailableTimeSlots() {
        return veterinarianTimeSlotRepository.findBySlotStatus(SlotStatus.AVAILABLE);
    }
//Lấy các Vet làm service Onl
    @Override
    public List<Veterinarian> getVeterinarian() {
        return veterinarianRepository.findVeterinarianByServiceTypeId();
    }
//Lấy tất cả Booking
    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }
//Lấy danh sách theo Id của Custom
    @Override
    public List<Booking> getBookingByUserId(int id) {
        List<Booking> bookings = bookingRepository.findByUser_Id(id);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Not booking found");
        }
        return bookings;
    }
//Lấy danh sách theo Id của Vet
    @Override
    public List<Booking> getBookingByVeterinarianId(int veterinarianId) {
    //Kiểm tra xem bác sĩ thú y có tồn tại không
        if (!veterinarianRepository.existsByUserId(veterinarianId)) {
            throw new RuntimeException("Veterinarian does not exist");
        }
    //Tìm các booking của bác sĩ thú y
        List<Booking> bookings = bookingRepository.findByVeterinarian_User_Id(veterinarianId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Not booking found");
        }
        return bookings;
    }
//Update Booking Status
    @Override
    public Booking updateBookingStatus(BookingStatusUpdateRequest request, Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (request.getStatus() != null) {
            // Check if the new status is COMPLETED and if there is an associated timeSlot
            if (request.getStatus() == BookingStatus.COMPLETED && booking.getSlot() != null) {
                VeterinarianTimeSlot timeSlot = booking.getSlot();
                timeSlot.setSlotStatus(SlotStatus.AVAILABLE); // Set slot status to AVAILABLE
                veterinarianTimeSlotRepository.save(timeSlot); // Save the updated timeSlot
            }
            booking.setStatus(request.getStatus());
        }
        return bookingRepository.save(booking);
    }
//Cancelled Booking
    @Override
    public Booking deleteBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check if the booking has an associated timeSlot
        if (booking.getSlot() != null) {
            VeterinarianTimeSlot timeSlot = booking.getSlot();
            timeSlot.setSlotStatus(SlotStatus.AVAILABLE); // Set slot status to AVAILABLE
            veterinarianTimeSlotRepository.save(timeSlot); // Save the updated timeSlot
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
