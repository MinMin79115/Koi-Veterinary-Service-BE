package com.swp391.crud_api_koi_veterinary.controller;

import com.swp391.crud_api_koi_veterinary.model.dto.request.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.swp391.crud_api_koi_veterinary.model.dto.response.AuthenticationResponse;
import com.swp391.crud_api_koi_veterinary.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    @Autowired
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthenticationResponse> usernamePasswordAuthentication(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return ResponseEntity.ok(authService.localAuthentication(authenticationRequest));
    }
    
    @PostMapping("register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody UserCreationRequest registerRequest) {
        return ResponseEntity.ok(authService.registerAuthentication(registerRequest));
    }
    
    @PostMapping("addstaff")
    public ResponseEntity<AuthenticationResponse> createStaff(@RequestBody StaffCreationRequest staffRequest) {
        return ResponseEntity.ok(authService.createStaffAuthentication(staffRequest));
    }

//API send reset password link
    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestBody EmailForgetRequest request) {
        try {
            authService.forgetPassword(request);
            return ResponseEntity.ok("A reset link have sent to your Email");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send reset link: " + e.getMessage());
        }
    }
//API đặt lại password
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest request) {
        try {
            authService.resetPassword(request);
            return ResponseEntity.ok("Password change successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to change Password" + e.getMessage());
        }
    }
}