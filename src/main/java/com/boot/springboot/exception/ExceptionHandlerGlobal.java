package com.boot.springboot.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice // pokazat' springy obrabot4ik isklu4enni

public class ExceptionHandlerGlobal {

    @ExceptionHandler(CustomValidException.class)
    public ResponseEntity<HttpStatus> customValidExceptionHandler(Exception exception) {
        log.error("Valid exception" + exception);
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HttpStatus> usernameNotFoundException(Exception exception) {
        log.error("Username not found: " + exception);
        return new ResponseEntity<>(HttpStatus.valueOf(401));
    }

    @ExceptionHandler(SameUserInDatabase.class)
    public ResponseEntity<HttpStatus> sameUserInDatabase(Exception exception) {
        log.error("User already exist with this login! " + exception);
        return new ResponseEntity<>(HttpStatus.valueOf(409));
    }

}
