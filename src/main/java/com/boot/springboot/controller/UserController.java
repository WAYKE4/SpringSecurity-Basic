package com.boot.springboot.controller;


import com.boot.springboot.Model.User;
import com.boot.springboot.Model.dto.UserCreateDto;
import com.boot.springboot.annotation.TimeAop;
import com.boot.springboot.exception.CustomValidException;
import com.boot.springboot.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/user") // все методы этого контроллера начинаются /user
public class UserController {
    //TODO:C(POST)R(GET)U(POST)D(POST)

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello") // HTTP GET
    public String helloPage() {
        //service - bisnes-logica(4to-to delaem , sho nas prosyat
        boolean ok = new Random().nextBoolean();
        return ok ? "success" : "failure";
    }

    @Operation(summary = "otdaet vsex userov iz bazi dannix")
    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "404", description = "user ne naiden"),
            @ApiResponse(responseCode = "200", description = "user naiden"),
            @ApiResponse(responseCode = "500", description = "4to-to slomalos'")
    })
    @TimeAop
    public ResponseEntity<List<User>> getAllUsers(HttpServletResponse response) {
        log.info("start method getAllUsers");
        return new ResponseEntity<>(userService.getALLUsers(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/info")
    public ResponseEntity<User> getInfoAboutCurrentUser(Principal principal) {
        //Dostat' iz SecurityContext usera i yznaem sho eto za 4el
        // dostaet iz repositoriya po
        Optional<User> result = userService.getInfoAboutCurrentUser(principal.getName());
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result.get(), HttpStatus.OK);

    }

    @TimeAop
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") @Parameter(description = "id usera kotorogo ishem") Long id) { //PathVariable - esli xotim dostat' 4to-to iz pyti
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id) ? "success" : "failure";
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserCreateDto user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomValidException(bindingResult.getAllErrors().toString());
        }
        if (userService.createUser(user)) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.updateUser(user) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }

    @GetMapping("/sort/{field}")
    public String getAllUsersAndSortById(ModelMap modelMap, @PathVariable String field) {
        List<User> users = userService.getUsersAndsortByField(field);
        modelMap.addAttribute("users", users);
        return users.isEmpty() ? "empty" : "get_users";
    }

    @GetMapping("/paging/{size}/{page}")
    public String getAllusersWithPagination(ModelMap modelMap, @PathVariable Integer size, @PathVariable Integer page) {
        List<User> users = userService.getUsersWithPagination(size, page);
        modelMap.addAttribute("users", users);
        return users.isEmpty() ? "empty" : "get_users";
    }

    @GetMapping("/test")
    public String test() throws Exception {
        userService.testTransactional();
        return "get_users";
    }

    @Tag(name = "delete Methods")
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUserById(id) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT);
    }
}