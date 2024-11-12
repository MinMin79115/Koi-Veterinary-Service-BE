package com.swp391.crud_api_koi_veterinary.controller;

import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceTypeUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.model.entity.ServicesType;
import com.swp391.crud_api_koi_veterinary.service.Koi_vetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "Services", description = "Services management APIs")
public class ServicesController {
    private final Koi_vetService koi_vetService;

    //API tạo 1 service
    @PostMapping
    @Operation(summary = "Create a new service", description = "Creates a new service with the provided details")
    public Services createService(@RequestBody ServiceCreationRequest request){
        return koi_vetService.createService(request);
    }

    //API lấy danh sách services
    @GetMapping
    @Operation(summary = "Get all services", description = "Retrieves a list of all services")
    public List<Services> listServices(){
        return koi_vetService.getAllServices();
    }

    //API update
    @PutMapping("/{serviceId}")
    @Operation(summary = "Update a service", description = "Updates an existing service with the provided details")
    public Services updateService(@PathVariable int serviceId, @RequestBody ServiceUpdateRequest request){
        return koi_vetService.updateService(serviceId, request);
    }

    //API delete
    @DeleteMapping("/{serviceId}")
    @Operation(summary = "Delete a service", description = "Deletes a service by its ID")
    public String deleteService(@PathVariable int serviceId){
        koi_vetService.deleteService(serviceId);
        return "Service has been deleted";
    }

    //API lấy 1 service
    @GetMapping("/{serviceId}")
    @Operation(summary = "Get a service by ID", description = "Retrieves a service by its ID")
    public ResponseEntity<Optional<Services>> getServiceById(@PathVariable int serviceId) {
        Optional<Services> koiVetServices = koi_vetService.getServiceById(serviceId);
        return ResponseEntity.ok(koiVetServices);
    }

    @PutMapping("type/{serviceTypeId}")
    @Operation(summary = "Update a serviceType Price", description = "Updates an existing serviceType with the provided price")
    public ServicesType updateServiceTypePrice(@PathVariable int serviceTypeId, @RequestBody ServiceTypeUpdateRequest request){
        return koi_vetService.updateServiceType(serviceTypeId, request);
    }
}
