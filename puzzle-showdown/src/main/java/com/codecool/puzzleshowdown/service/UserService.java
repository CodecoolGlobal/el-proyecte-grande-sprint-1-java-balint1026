package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.AlreadyExistingUserException;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.model.user.User;
import com.codecool.puzzleshowdown.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private User saveUser(UserRegistrationDTO userRegistration){
        try{
            User user = new User(
                    userRegistration.firstName(),
                    userRegistration.lastName(),
                    userRegistration.userName(),
                    userRegistration.email(),
                    userRegistration.password()
            );

            return userRepository.save(user);
        } catch (Exception e){
            throw new AlreadyExistingUserException(userRegistration.userName());
        }

    }
}
