package com.swp391.crud_api_koi_veterinary.repository;

import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Integer> {
    
    @Query("SELECT v FROM Veterinarian v JOIN FETCH v.user u JOIN FETCH v.serviceTypeId st WHERE st.id = 1")
    List<Veterinarian> findVeterinarianByServiceTypeId();

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Veterinarian v JOIN v.user u WHERE u.id = :userId")
    boolean existsByUserId(int userId);

}
