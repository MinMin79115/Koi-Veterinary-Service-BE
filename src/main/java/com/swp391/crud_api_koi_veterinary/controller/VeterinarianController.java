package com.swp391.crud_api_koi_veterinary.controller;

import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianSlotCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianSlotUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;
import com.swp391.crud_api_koi_veterinary.service.VeterinarianService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veterinarian")
@RequiredArgsConstructor
public class VeterinarianController {

    private final VeterinarianService veterinarianService;

//API tạo vet
    @PostMapping("/{userId}")
    public Veterinarian createVeterinarian(@RequestBody VeterinarianCreationRequest request, @PathVariable int userId) {
        return veterinarianService.createVeterinarian(request, userId);
    }

//API tạo slot làm việc cho vet
    @PostMapping("/slot")
    public VeterinarianTimeSlot createVeterinarianSlot(@RequestBody VeterinarianSlotCreationRequest request) {
        return veterinarianService.createVeterinarianSlot(request);
    }

//API lấy tất cả bác sĩ đã phân công và chưa phân công ServiceType
    @GetMapping
    public List<Veterinarian> getAllVeterinarian() {
        return veterinarianService.getAllVeterinarian();
    }

//API lấy tất cả các slot làm việc
    @GetMapping("/slot")
    public List<VeterinarianTimeSlot> getAllSlots() {
        return veterinarianService.getAllSlots();
    }

//API xóa bác sĩ trong bảng phân công
    @DeleteMapping("/{veterinarianId}")
    public String deleteVeterinarian(@PathVariable int veterinarianId) {
        veterinarianService.deleteVeterinarian(veterinarianId);
        return "Veterinarian has been deleted";
    }

//API xóa bác sĩ trong bảng phân công
    @DeleteMapping("/slot/{slotId}")
    public String deleteSlot(@PathVariable int slotId) {
        veterinarianService.deleteSlot(slotId);
        return "Slot has been deleted";
    }

//API update bác sĩ
    @PutMapping("/{veterinarianId}")
    Veterinarian updateVet(@PathVariable int veterinarianId, @RequestBody VeterinarianUpdateRequest request) {
        return veterinarianService.updateVet(veterinarianId, request);
    }

//API update vetSlot
    @PutMapping("/slot/{slotId}")
    VeterinarianTimeSlot updateSlot(@PathVariable int slotId, @RequestBody VeterinarianSlotUpdateRequest request) {
        return veterinarianService.updateVetSlot(slotId, request);
    }
}
