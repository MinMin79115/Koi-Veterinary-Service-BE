package com.swp391.crud_api_koi_veterinary.repository;

import com.swp391.crud_api_koi_veterinary.enums.BookingStatus;
import com.swp391.crud_api_koi_veterinary.model.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    // Tìm tất cả các booking của một người dùng cụ thể
    List<Booking> findByUser_Id(int id);

    // Tìm tất cả các booking của một bác sĩ thú y cụ thể
    List<Booking> findByVeterinarian_User_Id(int Id);

    // Tìm tất cả các booking theo trạng thái
    //List<Booking> findByStatus(BookingStatus status);

    // Bạn có thể thêm các phương thức truy vấn tùy chỉnh khác nếu cần
    @Query("SELECT b FROM Booking b WHERE b.user.username = :username AND b.bookingTime >= :startDate AND b.bookingTime <= :endDate")
    List<Booking> findUserBookingsInDateRange(@Param("username") String username, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT b FROM Booking b WHERE b.status = :status")
    List<Booking> findBookingsByStatus(@Param("status") BookingStatus status);
}
