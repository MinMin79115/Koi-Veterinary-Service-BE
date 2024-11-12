package com.swp391.crud_api_koi_veterinary.service.implement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import com.swp391.crud_api_koi_veterinary.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var result = userRepository.findUserByUsername(username).orElseThrow();
            return new User(result.getUsername(), result.getPassword(), rolesToAuthority(result));
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Username or password is incorrect", e);
        }
    }

    private Collection<GrantedAuthority> rolesToAuthority(UserAccount user) {
        var roleList = new ArrayList<GrantedAuthority>();
        roleList.add(new SimpleGrantedAuthority(String.format("ROLE_%s", user.getRole())));
        return roleList;
    }
}
