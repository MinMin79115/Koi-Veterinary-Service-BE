package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceTypeUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesType;
import com.swp391.crud_api_koi_veterinary.repository.ServiceRepository;
import com.swp391.crud_api_koi_veterinary.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class Koi_vetService{
    @Autowired
    private final ServiceRepository serviceRepository;
    private final ServiceTypeRepository serviceTypeRepository;

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

//6. Update service Type
    public ServicesType updateServiceType(int serviceTypeId, ServiceTypeUpdateRequest request) {
        ServicesType service = serviceTypeRepository.findById(serviceTypeId)
                .orElseThrow(() -> new RuntimeException("Service Type not found"));

        if (request.getPrice() != null) {
            if (request.getPrice().compareTo(new BigDecimal("1000")) >= 0) {
                service.setPrice(request.getPrice());
            } else {
                throw new RuntimeException("Price must be greater than 1000");
            }
        }

        return serviceTypeRepository.save(service);
    }
//7. Lấy tất cả service type
    public List<ServicesType> getAllServiceType() {
    return serviceTypeRepository.findAll();
}

}
