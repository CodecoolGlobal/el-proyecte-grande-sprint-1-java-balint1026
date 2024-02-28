package com.codecool.puzzleshowdown.controller;

import com.codecool.puzzleshowdown.dto.user.*;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.security.jwt.JwtUtils;
import com.codecool.puzzleshowdown.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, JwtUtils jwtUtils, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable long userId){
        return userService.getUserById(userId);
    }

    @GetMapping("/leaderboard")
    public List<UserLeaderBoard> getLeaderBoard(){
        return userService.getUsersByRoleSorted();
    }

//    @PostMapping("/registration")
//    public ResponseEntity<?> saveUser(@RequestBody NewUserDTO userRegistrationDTO){
//        try{
//            UserLoginResponseDTO userResponse = userService.saveUser(userRegistrationDTO);
//            return ResponseEntity.ok(userResponse);
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
    @PostMapping("/registration")
    public ResponseEntity<?> createUser(@RequestBody NewUserDTO newUserDTO) {
        try{
            userService.saveUser(newUserDTO);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLoginDTO userLoginDTO){
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.username(), userLoginDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication); //jwt Token
        User user = userService.getUser(jwtUtils.getUserNameFromJwtToken(jwt));
        return ResponseEntity.ok(new UserLoginResponseDTO(user.getId(), user.username() , jwt, user.getImage()));
    }

//    @GetMapping("/profile/{userName}")
//    public ResponseEntity<?> getUser(@PathVariable String userName){
//        try{
//            Optional<User> userData = userService.getUserData(userName);
//            return ResponseEntity.ok(userData);
//
//        } catch (Exception e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PatchMapping("/rating/{userId}")
    public boolean patchUserRating(@PathVariable long userId,@RequestParam int rating){
        return userService.patchRating(userId, rating);
    }

    @PutMapping("/savePuzzle/{userId}/{puzzleId}")
    public void postPuzzleToUser( @PathVariable long userId, @PathVariable String puzzleId){
        userService.savePuzzleToUser(userId, puzzleId);
    }
}
