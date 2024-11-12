package com.swp391.crud_api_koi_veterinary.service.implement;

import java.time.LocalDateTime;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swp391.crud_api_koi_veterinary.enums.Role;
import com.swp391.crud_api_koi_veterinary.model.dto.request.AuthenticationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.EmailForgetRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.ResetPasswordRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.StaffCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.UserCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.response.AuthenticationResponse;
import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;
import com.swp391.crud_api_koi_veterinary.service.AuthService;
import com.swp391.crud_api_koi_veterinary.service.EmailService;
import com.swp391.crud_api_koi_veterinary.service.JwtService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthenticationResponse localAuthentication(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            var user = userRepository.findUserByUsername(authentication.getName()).orElseThrow();
            var accessToken = jwtService.generateAccessToken(authentication);

            return AuthenticationResponse.builder()
                    .finishTime(LocalDateTime.now())
                    .id(user.getId())
                    .accessToken(accessToken)
                    .role(String.valueOf(user.getRole()))
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .fullname(user.getFullname())
                    .build();
        } catch (BadCredentialsException | UsernameNotFoundException e) {
            throw new RuntimeException("Username or password is incorrect", e);
        }
    }

    public AuthenticationResponse registerAuthentication(UserCreationRequest request) {
        if (userRepository.findUserByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already in use");
        }

        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(Role.CUSTOMER); // Mặc định role là CUSTOMER

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        String accessToken = jwtService.generateAccessToken(authentication);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .role(String.valueOf(user.getRole()))
                .finishTime(LocalDateTime.now())
                .build();
    }

    @Override
    public AuthenticationResponse createStaffAuthentication(StaffCreationRequest request) {
        if (userRepository.findUserByUsername(request.getPhone()).isPresent()) {
            throw new RuntimeException("Phone number is already in use");
        }

        UserAccount user = new UserAccount();
        user.setUsername(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mật khẩu mặc định
        user.setFullname(request.getFullname());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setRole(request.getRole());

        userRepository.save(user);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getPhone(), request.getPassword()));

        String accessToken = jwtService.generateAccessToken(authentication);
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .role(String.valueOf(user.getRole()))
                .finishTime(LocalDateTime.now())
                .build();
    }

    // quên mk
    public void forgetPassword(EmailForgetRequest request) throws MessagingException {
        UserAccount userAccount = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (userAccount != null) {
            emailService.sendHtmlEmail(request.getEmail(), request.getSubject(), request.getBody());
        }
    }

    // Đặt lại password
    @Override
    public void resetPassword(ResetPasswordRequest request) {
        UserAccount userAccount = userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userAccount != null) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
            userRepository.save(userAccount);
        }
    }
}
