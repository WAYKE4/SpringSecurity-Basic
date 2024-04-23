package com.boot.springboot.Repository;

import com.boot.springboot.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Query(nativeQuery = true, value = "SELECT * FROM users")
    List<User> customGetAllUsers();
    User getUserByUsername(String username);
}
