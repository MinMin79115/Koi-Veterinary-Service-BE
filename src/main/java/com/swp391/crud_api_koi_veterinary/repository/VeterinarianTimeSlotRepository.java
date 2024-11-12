package com.swp391.crud_api_koi_veterinary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;

@Repository
public interface VeterinarianTimeSlotRepository extends JpaRepository<VeterinarianTimeSlot, Integer> {

    List<VeterinarianTimeSlot> findBySlotStatus(SlotStatus slotStatus);

    // Thêm phương thức xóa TimeSlot theo veterinarianId
    void deleteByVeterinarian(Veterinarian veterinarian);

    boolean existsByVeterinarianAndSlotStatus(Veterinarian veterinarian, SlotStatus slotStatus);
}
