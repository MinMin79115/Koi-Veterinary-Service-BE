package com.swp391.crud_api_koi_veterinary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.swp391.crud_api_koi_veterinary.model.entity.Services;

@Repository
public interface ServiceRepository extends JpaRepository<Services, Integer> {

}
