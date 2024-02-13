package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/user/registration")
    public ResponseEntity<?> saveUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        try{
            userService.saveUser(userRegistrationDTO);
            return ResponseEntity.ok(true);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO){
        try{
            boolean isValidLogin = userService.userValidation(userLoginDTO);
            return ResponseEntity.ok(isValidLogin);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
