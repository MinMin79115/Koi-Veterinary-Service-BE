package com.swp391.crud_api_koi_veterinary.model.dto.request;

import lombok.Data;

@Data
public class VeterinarianSlotCreationRequest {
    private int veterinarianId;
    private int slotTimeId;
}
