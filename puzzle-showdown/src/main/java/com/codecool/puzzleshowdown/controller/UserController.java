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
            return ResponseEntity.ok(new UserLoginResponseDTO("lmate", "data:image/webp;base64,UklGRsACAABXRUJQVlA4ILQCAAAQHwCdASrMAMwAPpFIoEolpKOhqVKoILASCWlu4XSRxnshYzO3MPRiTayksTWHGZ5Qtwq0NteTXRMU5nfIQLfgcdiTmDwkLqmxAQo2icCNUfIQF1csCUBqjZD/PEUc6hyUlbRMdh0jd4vXOY2xZvGIWwiy3HFP5I+PzufW18bJHGdeax/BkPorWE99Z5I+GZysuVELOKbqoB87bnkj4/GTmVEctFTnjTCvtTcTbkGEC31RIiA+vBvZyU4sSELR8PkDIJSOwGGpncDkT8tTRCy3fIyxoY69TDal6ni76lspQa8QXe7dwJcJw+sWGgOrhqfIzNPGknNVUOiuDIQLp7KqAAD+/vOywLPVHNOm0o2aci22oB9PA9jFoCjdym6EqSMQYxaEdsaSagbI9dNEO1M33HK5clYkOwTsx2n1ypzYw0svh3uQHx+5n5xpwrzOWMHEH28AcPVPlwyQJk63CGefi1wxKo0PQfxtXCP5QQKd0qpK2n5yV9w0iAJaBJwM6xDGsDCFOsksDXd2GMwbJWSi6RJQ3NJfDOy5v7HcT/D6Ce/55stFf+0JtQT6xyGW9vixBJ4G1kJ1nyZd2dRHpeV3TZE6lfXp3op58h80SM/0IyHKNWVueD/blUdoFdHZXAbAUGsRoBJCZrfHvVmnxbT4mfkrEe6eFnkqNrha0Dzxzl5CPQ5QLqrIbSMfMHlYqNbhQ6YWNtkutV1rocErGvUUZpgR2rQejHSXoQu6iDEYdVMn3bQTBkpkLOzCZIYgDGiavfTwQWp+caGtsgbMgDHLcunk4dw+3a3Yg155G9nIzpW4G076xgTmDqcAVxqi0pcJM/qyT3FYdfVKeGTxu5hmRfZ3f7NmvByy+ZIMGMwF+p2x0m/Us9896J44QpHJDmLA4WfQdl73TZnk4EIVfq9aYAAAAA=="));
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
