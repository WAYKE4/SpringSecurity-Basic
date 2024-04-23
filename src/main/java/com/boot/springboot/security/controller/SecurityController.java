package com.boot.springboot.security.controller;

import com.boot.springboot.security.model.RegistrationDto;
import com.boot.springboot.security.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
    private UserSecurityService userSecurityService;

    @Autowired
    public SecurityController(UserSecurityService userSecurityService) {
        this.userSecurityService = userSecurityService;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody RegistrationDto registrationDto) {
        userSecurityService.registration(registrationDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
