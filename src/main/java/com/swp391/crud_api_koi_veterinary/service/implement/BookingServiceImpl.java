package com.swp391.crud_api_koi_veterinary.service.implement;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.swp391.crud_api_koi_veterinary.enums.BookingStatus;
import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import com.swp391.crud_api_koi_veterinary.enums.VetState;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.BookingStatusUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;
import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;
import com.swp391.crud_api_koi_veterinary.repository.BookingRepository;
import com.swp391.crud_api_koi_veterinary.repository.ServicesDetailRepository;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianTimeSlotRepository;
import com.swp391.crud_api_koi_veterinary.service.BookingService;

import lombok.RequiredArgsConstructor;

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

        VeterinarianTimeSlot timeSlot = request.getSlotId() != null
                ? veterinarianTimeSlotRepository.findById(request.getSlotId())
                        .orElseThrow(() -> new RuntimeException("Slot not found"))
                : null;

        Veterinarian veterinarian = request.getVeterinarianId() != null
                ? veterinarianRepository.findById(request.getVeterinarianId())
                        .orElseThrow(() -> new RuntimeException("Veterinarian not found"))
                : null;

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setServicesDetail(servicesDetail);
        if (veterinarian != null) {
            booking.setVeterinarian(veterinarian);
            if (timeSlot == null) {
            veterinarian.setState(VetState.WORKING);
            }
            veterinarianRepository.save(veterinarian);
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
        if (request.getServiceTime() != null) {
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

    // Lấy các Vet làm service Onl
    @Override
    public List<Veterinarian> getVeterinarian() {
        return veterinarianRepository.findVeterinarianByServiceTypeId();
    }

    // Lấy tất cả Booking
    @Override
    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }

    // Lấy danh sách theo Id của Custom
    @Override
    public List<Booking> getBookingByUserId(int id) {
        List<Booking> bookings = bookingRepository.findByUser_Id(id);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Not booking found");
        }
        return bookings;
    }

    // Lấy danh sách theo Id của Vet
    @Override
    public List<Booking> getBookingByVeterinarianId(int veterinarianId) {
        // Kiểm tra xem bác sĩ thú y có tồn tại không
        if (!veterinarianRepository.existsByUserId(veterinarianId)) {
            throw new RuntimeException("Veterinarian does not exist");
        }
        // Tìm các booking của bác sĩ thú y
        List<Booking> bookings = bookingRepository.findByVeterinarian_User_Id(veterinarianId);
        if (bookings.isEmpty()) {
            throw new RuntimeException("Not booking found");
        }
        return bookings;
    }

    // Update Booking Status
    @Override
    public Booking updateBookingStatus(BookingStatusUpdateRequest request, Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (request.getVeterinarianId() != null){
            Veterinarian veterinarian1 = veterinarianRepository.findById(request.getVeterinarianId())
                    .orElseThrow(() -> new RuntimeException("Veterinarian not found"));
            booking.setVeterinarian(veterinarian1);
        }

        if (request.getNote() != null) {
            booking.setNote(request.getNote());
        }

        if (request.getStatus() != null) {
            // Check if the new status is COMPLETED and if there is an associated timeSlot
            if (request.getStatus() == BookingStatus.COMPLETED && booking.getSlot() != null) {
                VeterinarianTimeSlot timeSlot = booking.getSlot();
                timeSlot.setSlotStatus(SlotStatus.AVAILABLE); // Set slot status to AVAILABLE
                veterinarianTimeSlotRepository.save(timeSlot); // Save the updated timeSlot
            } else if (request.getStatus() == BookingStatus.COMPLETED) {
                Veterinarian veterinarian = booking.getVeterinarian();
                veterinarian.setState(VetState.ONLINE);
                veterinarianRepository.save(veterinarian);
            }
            booking.setStatus(request.getStatus());
        }
        return bookingRepository.save(booking);
    }

    // Cancelled Booking
    @Override
    public Booking deleteBooking(Integer bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Check if the booking has an associated timeSlot
        if (booking.getSlot() != null) {
            VeterinarianTimeSlot timeSlot = booking.getSlot();
            timeSlot.setSlotStatus(SlotStatus.AVAILABLE);
            veterinarianTimeSlotRepository.save(timeSlot);
        } else if (booking.getVeterinarian() != null) {
            Veterinarian veterinarian = booking.getVeterinarian();
            veterinarian.setState(VetState.ONLINE);
            veterinarianRepository.save(veterinarian);
        }

        booking.setStatus(BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }
}
