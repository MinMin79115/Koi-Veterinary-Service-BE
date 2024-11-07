package com.swp391.crud_api_koi_veterinary.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swp391.crud_api_koi_veterinary.enums.Role;
import com.swp391.crud_api_koi_veterinary.model.dto.request.UserCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.dto.request.UserUpdateRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.model.entity.Veterinarian;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianRepository;
import com.swp391.crud_api_koi_veterinary.repository.VeterinarianTimeSlotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

    @Autowired
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VeterinarianRepository veterinarianRepository;
    private final VeterinarianTimeSlotRepository veterinarianTimeSlotRepository;
    private final VeterinarianService veterinarianService; // Thêm VeterinarianService

    // Thêm 1 account
    public UserAccount createUser(UserCreationRequest request) {
        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setEmail(request.getEmail());
        user.setFullname(request.getFullname());
        user.setRole(Role.CUSTOMER); // Mặc định là CUSTOMER
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    // Lấy danh sách account STAFF
    public List<UserAccount> getUserAccount() {
        // Thay đổi để tìm kiếm cả STAFF và VETERINARIAN
        return userRepository.findByRoleIn(Arrays.asList("STAFF", "VETERINARIAN"));
    }

    // xem thông tin theo id
    public UserAccount getUserInfo(int userId) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Đặt mật khẩu thành null để không trả về
        user.setPassword(null);

        return user;
    }

    // Xóa 1 account theo id
    public void deleteAccount(int userId) {
        UserAccount user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Kiểm tra xem người dùng có phải là Veterinarian không
        if (user.getRole() == Role.VETERINARIAN) {
            Optional<Veterinarian> optionalVeterinarian = veterinarianRepository.findByUserId(userId);
            if (optionalVeterinarian.isPresent()) { // Nếu có gọi deleteVeterinarian
                Veterinarian veterinarian = optionalVeterinarian.get();
                int veterinarianId = veterinarian.getVeterinarianId();
                // Gọi phương thức deleteVeterinarian
                veterinarianService.deleteVeterinarian(veterinarianId);
            }
            userRepository.deleteById(userId);
        } else {
            userRepository.deleteById(userId); // Xóa tài khoản người dùng
        }
    }

    // Update thông tin cá nhân
    public UserAccount updateUser(int userId, UserUpdateRequest request) {
        UserAccount userAccount = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Chỉ cập nhật mật khẩu nếu có mật khẩu mới được cung cấp
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            userAccount.setPassword(passwordEncoder.encode(request.getPassword()));
        }
        if (request.getUsername() != null) {
            userAccount.setUsername(request.getUsername());
        }
        // Các trường khác được cập nhật nếu có giá trị mới
        if (request.getEmail() != null) {
            userAccount.setEmail(request.getEmail());
        }
        if (request.getPhone() != null) {
            userAccount.setPhone(request.getPhone());
        }
        if (request.getAddress() != null) {
            userAccount.setAddress(request.getAddress());
        }
        if (request.getFullname() != null) {
            userAccount.setFullname(request.getFullname());
        }

        return userRepository.save(userAccount);
    }
}
