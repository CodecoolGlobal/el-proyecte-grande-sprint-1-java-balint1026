package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.custom_exception.NonExistingUserException;
import com.codecool.puzzleshowdown.dto.user.UserDTO;
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

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId){
        return userService.getUserById(userId);
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

    @GetMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String name, @RequestParam String pass){
        try {
            UserLoginDTO userLoginDTO = new UserLoginDTO(name, pass);
            UserLoginResponseDTO validUser = userService.userValidation(userLoginDTO);
            return ResponseEntity.ok().body(validUser);
        }catch (NonExistingUserException non){
            return ResponseEntity.badRequest().body(non);
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
    @PatchMapping("/rating/{userId}")
    public boolean patchUserRating(@PathVariable long userId,@RequestParam int rating){
        return userService.patchRating(userId, rating);
    }

    @PutMapping("/savePuzzle/{userId}/{puzzleId}")
    public void postPuzzleToUser( @PathVariable long userId, @PathVariable String puzzleId){
        userService.savePuzzleToUser(userId, puzzleId);
    }
}
