package com.swp391.crud_api_koi_veterinary;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.swp391.crud_api_koi_veterinary.controller.ServicesController;
import com.swp391.crud_api_koi_veterinary.service.Koi_vetService;
import com.swp391.crud_api_koi_veterinary.model.entity.Services;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ServiceUpdateRequest;

@ExtendWith(MockitoExtension.class)
public class ServicesControllerTest {

    @Mock
    private Koi_vetService koi_vetService;

    @InjectMocks
    private ServicesController servicesController;

    @BeforeEach
    void setUp() {
        // No need to call MockitoAnnotations.openMocks(this) when using @ExtendWith(MockitoExtension.class)
    }

    @Test
    void testGetServices() {
        // Arrange
        Services service1 = new Services(1, "Service 1", "Description 1");
        Services service2 = new Services(2, "Service 2", "Description 2");
        List<Services> mockServices = Arrays.asList(service1, service2);

        when(koi_vetService.getAllServices()).thenReturn(mockServices);

        // Act
        List<Services> result = servicesController.listServices();

        // Assert
        assertNotNull(result, "The services list should not be null");
        assertEquals(2, result.size(), "The services list should contain 2 services");
        assertEquals("Service 1", result.get(0).getServiceName());
        assertEquals("Service 2", result.get(1).getServiceName());
    }

    @Test
    void testGetServicesById_ExistingServices() {
        // Arrange
        int serviceId = 1;
        Services mockService = new Services(serviceId, "Test Service", "Test Description");

        when(koi_vetService.getServiceById(serviceId)).thenReturn(Optional.of(mockService));

        // Act
        ResponseEntity<Optional<Services>> response = servicesController.getServiceById(serviceId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(serviceId, response.getBody().get().getServiceId());
        assertEquals("Test Service", response.getBody().get().getServiceName());
    }

    @Test
    void testGetServicesById_NonExistingServices() {
        // Arrange
        int serviceId = 999;
        when(koi_vetService.getServiceById(serviceId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Optional<Services>> response = servicesController.getServiceById(serviceId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testCreateService() {
        // Arrange
        Services savedService = new Services(1, "New Service", "New Description");
        ServiceCreationRequest request = new ServiceCreationRequest("New Service", "New Description");
        when(koi_vetService.createService(request)).thenReturn(savedService);

        // Act
        Services response = servicesController.createService(request);

        // Assert
        assertEquals(1, response.getServiceId()); // id là 1
        assertEquals("New Service", response.getServiceName()); // name là "New Service"
        assertEquals("New Description", response.getServiceDescription()); // description là "New Description"
    }

    @Test
    void testUpdateService_ExistingService() {
        // Arrange
        int serviceId = 1;
        Services updatedService = new Services(serviceId, "Updated Service", "Updated Description");
        ServiceUpdateRequest request = new ServiceUpdateRequest("Updated Service", "Updated Description");
        when(koi_vetService.updateService(serviceId, request)).thenReturn((updatedService));
        // Act
        Services response = servicesController.updateService(serviceId, request);

        // Assert
        assertEquals(1, response.getServiceId()); // id là 1
        assertEquals("Updated Service", response.getServiceName()); // name là "Updated Service"
        assertEquals("Updated Description", response.getServiceDescription()); // description là "Updated Description"
    }

    @Test
    void testUpdateService_NonExistingService() {
        // Arrange
        int serviceId = 999;
        ServiceUpdateRequest request = new ServiceUpdateRequest("Updated Service", "Updated Description");
        when(koi_vetService.updateService(serviceId, request)).thenReturn(null);

        // Act
        Services response = servicesController.updateService(serviceId, request);

        // Assert
        assertNull(response);
    }

    // Add more test methods here...
}
