package com.boot.springboot.service;


import com.boot.springboot.Model.User;
import com.boot.springboot.Model.dto.UserCreateDto;
import com.boot.springboot.Repository.UserRepository;
import com.boot.springboot.security.model.UserSecurity;
import com.boot.springboot.security.repository.UserSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserSecurityRepository userSecurityRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserSecurityRepository userSecurityRepository) {
        this.userRepository = userRepository;
        this.userSecurityRepository = userSecurityRepository;
    }

    public Optional<User> getInfoAboutCurrentUser(String username) {
        Optional<UserSecurity> userSecurity = userSecurityRepository.findByUserLogin(username);
        if (userSecurity.isEmpty()){
            return Optional.empty();
        }
        return userRepository.findById(userSecurity.get().getUserId());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getALLUsers() {
        return userRepository.customGetAllUsers();
    }

    @PreAuthorize("hasRole('ADMIN')")

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Boolean deleteUserById(Long id) {
        userRepository.deleteById(id);
        return getUserById(id).isEmpty();
    }

    public Boolean createUser(UserCreateDto userFromDto) {
        User user = new User();
        user.setUsername(userFromDto.getUsername());
        user.setAge(userFromDto.getAge());
        user.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        user.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        User createdUser = userRepository.save(user);
        return getUserById(createdUser.getId()).isPresent();
    }

    public boolean updateUser(User user) {
        Optional<User> userFromDBOptional = userRepository.findById(user.getId());
        if (userFromDBOptional.isPresent()) {
            User userFromDB = userFromDBOptional.get();
            if (user.getUsername() != null) {
                userFromDB.setUsername(user.getUsername());
            }
            if (user.getUserPassword() != null) {
                userFromDB.setUserPassword(user.getUserPassword());
            }
            if (user.getAge() != null) {
                userFromDB.setAge(user.getAge());
            }
            userFromDB.setChanged(Timestamp.valueOf(LocalDateTime.now()));
            User updatedUser = userRepository.saveAndFlush(userFromDB);
            return userRepository.equals(updatedUser);
        }
        return false;
    }

    public List<User> getUsersAndsortByField(String field) {
        return userRepository.findAll(Sort.by(field));
    }

    public List<User> getUsersWithPagination(int size, int page) {
        return userRepository.findAll(Pageable.ofSize(size).withPage(page)).getContent();
    }

    @Transactional(rollbackFor = Exception.class)
    public void testTransactional() throws Exception {
        User userOne = new User();
        User userTwo = new User();

        userOne.setUsername("keyke4");
        userOne.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        userOne.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        userOne.setAge(50);

        userTwo.setUsername("max");
        userTwo.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        userTwo.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        userTwo.setAge(25);

        userRepository.save(userOne);
        if (true) throw new Exception();
        userRepository.save(userTwo);
    }
}