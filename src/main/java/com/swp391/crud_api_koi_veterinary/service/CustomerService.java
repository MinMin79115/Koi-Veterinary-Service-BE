package com.swp391.crud_api_koi_veterinary.service;

import com.swp391.crud_api_koi_veterinary.model.dto.request.UserCreationRequest;
import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Thêm 1 account
    public UserAccount createUser(UserCreationRequest request) {
        UserAccount user = new UserAccount();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Mã hóa mật khẩu
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole("CUSTOMER"); // Mặc định là CUSTOMER
        user.setAddress(request.getAddress());
        user.setPhone(request.getPhone());
        return userRepository.save(user);
    }

    //Lấy danh sách account
    public List<UserAccount> getUserAccount(){
        return userRepository.findAll();
    }

    //xem thông tin theo id
    public UserAccount getUserInfo(int userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    //Xóa 1 account theo id
    public void deleteAccount(int userId){
        userRepository.deleteById(userId);
    }
}
