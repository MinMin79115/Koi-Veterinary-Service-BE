package com.swp391.crud_api_koi_veterinary.model.dto.request;

import com.swp391.crud_api_koi_veterinary.enums.SlotStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VeterinarianSlotUpdateRequest {
    private Integer veterinarianId;
    private Integer slotTimeId;
    SlotStatus status;
}
