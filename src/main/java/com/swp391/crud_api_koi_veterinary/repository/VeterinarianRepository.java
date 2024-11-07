package com.swp391.crud_api_koi_veterinary.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer> {

    @Query("SELECT v FROM Veterinarian v JOIN FETCH v.user u JOIN FETCH v.serviceTypeId st WHERE st.id = 1")
    List<Veterinarian> findVeterinarianByServiceTypeId();

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Veterinarian v JOIN v.user u WHERE u.id = :userId")
    boolean existsByUserId(int userId);

    // Thêm phương thức tìm Veterinarian theo userId
    @Query("SELECT v FROM Veterinarian v WHERE v.user.id = :userId")
    Optional<Veterinarian> findByUserId(@Param("userId") int userId);

}
