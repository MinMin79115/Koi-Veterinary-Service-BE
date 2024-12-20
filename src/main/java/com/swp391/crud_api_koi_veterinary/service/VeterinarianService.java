package com.swp391.crud_api_koi_veterinary.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp391.crud_api_koi_veterinary.enums.ServiceType;
import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import com.swp391.crud_api_koi_veterinary.enums.VetState;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianSlotCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianSlotUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.VeterinarianUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesType;
import com.swp391.crud_api_koi_veterinary.model.entity.TimeSlot;
import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.model.entity.VeterinarianTimeSlot;
import com.swp391.crud_api_koi_veterinary.repository.BookingRepository;
import com.swp391.crud_api_koi_veterinary.repository.ServiceTypeRepository;
import com.swp391.crud_api_koi_veterinary.repository.TimeSlotRepository;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianTimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeterinarianService {

    @Autowired
    private final VeterinarianRepository veterinarianRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final VeterinarianTimeSlotRepository veterinarianTimeSlotRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    // Tạo Vet
    public Veterinarian createVeterinarian(VeterinarianCreationRequest request, int userId) {

        // Kiểm tra xem userId đã tồn tại trong Veterinarian chưa
        if (veterinarianRepository.existsByUserId(userId)) {
            throw new RuntimeException("Veterinarian is already exist");
        }

        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ServicesType servicesType = request.getServiceTypeId() != null
                ? serviceTypeRepository.findById(request.getServiceTypeId())
                        .orElseThrow(() -> new RuntimeException("Service type not found"))
                : null;

        Veterinarian veterinarian = new Veterinarian();
        veterinarian.setUser(user);
        if (servicesType != null) {
            if (servicesType.getService_type() == ServiceType.Online) {
                veterinarian.setServiceTypeId(servicesType);
                veterinarian.setState(VetState.ONLINE);
            } else {
                veterinarian.setServiceTypeId(servicesType);
                veterinarian.setState(VetState.OFFLINE);
            }
        } else {
            veterinarian.setState(VetState.OFFLINE);
        }

        return veterinarianRepository.save(veterinarian);
    }

    // Tạo Vet Slot
    public VeterinarianTimeSlot createVeterinarianSlot(VeterinarianSlotCreationRequest request) {
        Veterinarian veterinarian = veterinarianRepository.findById(request.getVeterinarianId())
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

        TimeSlot timeSlot = timeSlotRepository.findById(request.getSlotTimeId())
                .orElseThrow(() -> new RuntimeException("Slot time not found"));

        VeterinarianTimeSlot veterinarianTimeSlot = new VeterinarianTimeSlot();
        veterinarianTimeSlot.setVeterinarian(veterinarian);
        veterinarianTimeSlot.setTimeSlot(timeSlot);
        veterinarianTimeSlot.setSlotStatus(SlotStatus.AVAILABLE);

        return veterinarianTimeSlotRepository.save(veterinarianTimeSlot);
    }

    // Danh sách tất cả Slot
    public List<VeterinarianTimeSlot> getAllSlots() {
        return veterinarianTimeSlotRepository.findAll();
    }

    // Lấy tất cả Vet
    public List<Veterinarian> getAllVeterinarian() {
        return veterinarianRepository.findAll();
    }

    // Delete Slot
    @Transactional
    public void deleteSlot(int slotId) {
        VeterinarianTimeSlot timeSlot = veterinarianTimeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        bookingRepository.updateSlotToNullBySlot(slotId);

        veterinarianTimeSlotRepository.deleteById(slotId);
    }

//Delete Vet
   @Transactional
   public void deleteVeterinarian(int veterinarianId) {
       Veterinarian veterinarian = veterinarianRepository.findById(veterinarianId)
               .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

       // Check if the veterinarian is working
       if (veterinarian.getState() == VetState.WORKING) {
           throw new RuntimeException("Unable to delete veterinarian: currently working.");
       }
       // Check if there are any slots of the vet with UNAVAILABLE status
       else if (veterinarianTimeSlotRepository.existsByVeterinarianAndSlotStatus(veterinarian, SlotStatus.UNAVAILABLE)) {
           throw new RuntimeException("Unable to delete veterinarian: a slot of this veterinarian is booked.");
       }

       // Cập nhật các booking liên quan
       bookingRepository.updateSlotToNullByVeterinarianId(veterinarianId);
       bookingRepository.updateVeterinarianToNull(veterinarianId);

       // Xóa các slot liên quan
       veterinarianTimeSlotRepository.deleteByVeterinarian(veterinarian);
       veterinarianRepository.deleteById(veterinarianId);
   }

//Update Vet
    public Veterinarian updateVet(int veterinarianId, VeterinarianUpdateRequest request) {
        Veterinarian veterinarian = veterinarianRepository.findById(veterinarianId)
                .orElseThrow(() -> new RuntimeException("Veterinarian not found"));

        ServicesType servicesType = serviceTypeRepository.findById(request.getServiceTypeId())
                .orElseThrow(() -> new RuntimeException("Type not found"));

        if (servicesType != null) {
            veterinarian.setServiceTypeId(servicesType);
        }

        return veterinarianRepository.save(veterinarian);
    }

    // Update Slot
    public VeterinarianTimeSlot updateVetSlot(int slotId, VeterinarianSlotUpdateRequest request) {
        VeterinarianTimeSlot slot = veterinarianTimeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

            if (slot.getSlotStatus() == SlotStatus.AVAILABLE) {
                if (request.getVeterinarianId() != null) {
                    Veterinarian veterinarian = veterinarianRepository.findById(request.getVeterinarianId())
                            .orElseThrow(() -> new RuntimeException("Veterinarian not found"));
                    slot.setVeterinarian(veterinarian);
             }

             if (request.getSlotTimeId() != null) {
                    TimeSlot timeSlot = timeSlotRepository.findById(request.getSlotTimeId())
                            .orElseThrow(() -> new RuntimeException("Slot time not found"));
                    slot.setTimeSlot(timeSlot);
             }


            if (request.getStatus() != null) {
                slot.setSlotStatus(request.getStatus());
            }
        } else {
            throw new RuntimeException("Can not update because slot is booked!");
        }
        return veterinarianTimeSlotRepository.save(slot);
    }

}
