package com.boot.springboot.security.service;

import com.boot.springboot.Model.User;
import com.boot.springboot.Repository.UserRepository;
import com.boot.springboot.exception.SameUserInDatabase;
import com.boot.springboot.security.model.RegistrationDto;
import com.boot.springboot.security.model.Roles;
import com.boot.springboot.security.model.UserSecurity;
import com.boot.springboot.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSecurityService {
    private final UserSecurityRepository userSecurityRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserSecurityService(UserSecurityRepository userSecurityRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userSecurityRepository = userSecurityRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
@Transactional(rollbackFor = Exception.class)
    public void registration(RegistrationDto registrationDto) {
        Optional<UserSecurity> security = userSecurityRepository.findByUserLogin(registrationDto.getLogin());
        if (security.isPresent()) {
            throw new SameUserInDatabase(registrationDto.getLogin());
        }
        User user = new User();
        user.setAge(registrationDto.getAge());
        user.setUsername(registrationDto.getUsername());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User savedUser = userRepository.save(user);

        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUserLogin(registrationDto.getLogin());
        userSecurity.setUserPassword(passwordEncoder.encode(registrationDto.getPassword()));
        userSecurity.setRole(Roles.USER);
        userSecurity.setUserId(savedUser.getId());
        userSecurity.setIsBlocked(false);
        userSecurityRepository.save(userSecurity);
    }
}
