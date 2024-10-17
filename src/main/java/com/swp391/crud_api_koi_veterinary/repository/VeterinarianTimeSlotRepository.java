package com.swp391.crud_api_koi_veterinary.repository;

import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarianTimeSlotRepository extends JpaRepository<VeterinarianTimeSlot, Integer> {

    List<VeterinarianTimeSlot> findBySlotStatus(SlotStatus slotStatus);

}
