package com.swp391.crud_api_koi_veterinary.model.dto.request;

import com.swp391.crud_api_koi_veterinary.enums.BookingStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingStatusUpdateRequest {
    Integer veterinarianId;
    String note;
    BookingStatus status;
}
