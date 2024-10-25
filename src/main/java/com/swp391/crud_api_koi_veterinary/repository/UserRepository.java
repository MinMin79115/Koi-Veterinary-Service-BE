package com.swp391.crud_api_koi_veterinary.repository;

import com.swp391.crud_api_koi_veterinary.model.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Integer> {
    @Query(value = "SELECT u FROM UserAccount u WHERE u.username = :username")
    Optional<UserAccount> findUserByUsername(@Param(value = "username") String username);

    @Query(value = "SELECT u FROM UserAccount u WHERE u.email = :email")
    Optional<UserAccount> findUserByEmail(@Param(value = "email") String email);

    List<UserAccount> findByRoleIn(List<String> list);
}