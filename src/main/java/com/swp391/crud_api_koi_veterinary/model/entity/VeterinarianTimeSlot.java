package com.swp391.crud_api_koi_veterinary.model.entity;

import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "veterinarian_time_slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VeterinarianTimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer slotId;

    @ManyToOne
    @JoinColumn(name = "slot_time_id", nullable = false)
    private TimeSlot timeSlot;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id", nullable = false)
    private Veterinarian veterinarian;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_status", nullable = false)
    private SlotStatus slotStatus;
}
