package com.swp391.crud_api_koi_veterinary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.swp391.crud_api_koi_veterinary.model.entity.ServicesDetail;

@Repository
public interface ServicesDetailRepository extends JpaRepository<ServicesDetail, Integer> {
    @Query("SELECT sd FROM ServicesDetail sd JOIN FETCH sd.serviceId JOIN FETCH sd.serviceTypeId")
    List<ServicesDetail> findAllWithDetails();
}
