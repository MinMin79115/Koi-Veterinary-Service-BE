package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.*;
import com.swp391.crud_api_koi_veterinary.model.dto.response.AuthenticationResponse;
import jakarta.mail.MessagingException;

public interface AuthService {
    AuthenticationResponse localAuthentication(AuthenticationRequest request);
    AuthenticationResponse registerAuthentication(UserCreationRequest request);
    AuthenticationResponse createStaffAuthentication(StaffCreationRequest request);
    void forgetPassword(EmailForgetRequest request) throws MessagingException;
    void resetPassword(ResetPasswordRequest request);
}