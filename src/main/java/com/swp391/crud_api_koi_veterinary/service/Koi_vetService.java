package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Koi_vetService{
    @Autowired
    private final ServiceRepository serviceRepository;

//1. Tạo 1 Service
    public Services createService(ServiceCreationRequest request) {
        Services service = new Services();
        service.setServiceName(request.getServiceName());
        service.setServiceDescription(request.getServiceDescription());
        return serviceRepository.save(service);
    }

//2. Lấy danh sách Services
    public List<Services> getAllServices() {
            return serviceRepository.findAll();
    }

//3. Update 1 Service theo Id
    public Services updateService(int serviceId, ServiceUpdateRequest request) {
        Services service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        
        // Cập nhật tên dịch vụ nếu có giá trị mới
        if (request.getServiceName() != null && !request.getServiceName().isEmpty()) {
            service.setServiceName(request.getServiceName());
        }
        
        // Cập nhật mô tả dịch vụ nếu có giá trị mới
        if (request.getServiceDescription() != null && !request.getServiceDescription().isEmpty()) {
            service.setServiceDescription(request.getServiceDescription());
        }
        
        return serviceRepository.save(service);
    }

    //4. Xóa 1 Service theo Id
    public void deleteService(int serviceId) {
        serviceRepository.deleteById(serviceId);
    }

    //5. Lấy 1 Service theo Id
    public Optional<Services> getServiceById(int serviceId) {
        return Optional.ofNullable(serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found")));
    }
}
