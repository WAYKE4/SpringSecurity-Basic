package com.boot.springboot;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
        title = "SpringBOOT-WAYKE4",
        description = "Some decription",
        contact = @Contact(name = "Maxim Drabysheuski",
                url = "086YaSnejni4elovek",
                email = "gitta.taaa@mail.ru"
        )))
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
