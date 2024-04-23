package com.boot.springboot.Model.dto;

import com.boot.springboot.annotation.isAdult;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class UserCreateDto {
    @NotNull
    @Size(min = 6, max = 15)
    private String username;
    @isAdult
    @NotNull
    private Integer age;
    @NotNull
    @Size(min = 5)
    private String userPassword;
}
