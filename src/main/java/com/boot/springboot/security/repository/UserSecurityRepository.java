package com.boot.springboot.security.repository;

import com.boot.springboot.security.model.UserSecurity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSecurityRepository extends JpaRepository< UserSecurity, Long> {

    Optional<UserSecurity> findByUserLogin(String userLogin);

}
