package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserLoginResponseDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> saveUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
        try{
            UserLoginResponseDTO userResponse = userService.saveUser(userRegistrationDTO);
            return ResponseEntity.ok(userResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO){
        try{
            /*
            boolean isValidLogin = userService.userValidation(userLoginDTO);
            return ResponseEntity.ok(isValidLogin);
            */
            // TESTING PURPOSES
            return ResponseEntity.ok(new UserLoginResponseDTO("lmate","default"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/profile/{userName}")
    public ResponseEntity<?> getUser(@PathVariable String userName){
        try{
            Optional<User> userData = userService.getUserData(userName);
            return ResponseEntity.ok(userData);

        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
