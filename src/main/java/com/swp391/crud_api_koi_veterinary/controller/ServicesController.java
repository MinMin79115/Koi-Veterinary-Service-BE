package com.swp391.crud_api_koi_veterinary.controller;


import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.service.Koi_vetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServicesController {
    private final Koi_vetService koi_vetService;

    //API tạo 1 service
    @PostMapping
    public Services createService(@RequestBody ServiceCreationRequest request){
        return koi_vetService.createService(request);
    }

    //API lấy danh sách services
    @GetMapping
    public List<Services> listServices(){
        return koi_vetService.getAllServices();
    }

    //API update
    @PutMapping("/{serviceId}")
    public Services updateService(@PathVariable int serviceId, @RequestBody ServiceUpdateRequest request){
        return koi_vetService.updateService(serviceId, request);
    }

    //API delete
    @DeleteMapping("/{serviceId}")
    public String deleteService(@PathVariable int serviceId){
        koi_vetService.deleteService(serviceId);
        return "Service has been deleted";
    }

    //API lấy 1 service
    @GetMapping("/{serviceId}")
    public ResponseEntity<Optional<Services>>getServiceById(@PathVariable int serviceId) {
        Optional<Services> koiVetServices = koi_vetService.getServiceById(serviceId);
        return ResponseEntity.ok(koiVetServices);
    }

}
