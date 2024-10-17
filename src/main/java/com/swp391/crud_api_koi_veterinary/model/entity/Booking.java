package com.swp391.crud_api_koi_veterinary.model.entity;

import com.swp391.crud_api_koi_veterinary.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserAccount user;

    @ManyToOne
    @JoinColumn(name = "services_detail_id", nullable = false)
    private ServicesDetail servicesDetail;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", nullable = true)
    private Veterinarian veterinarian;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private VeterinarianTimeSlot slot;

    @Column(name = "booking_time")
    private LocalDateTime bookingTime;

    @Column(name = "service_time")
    private LocalDate serviceTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private BookingStatus status;
}
