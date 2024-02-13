package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.AlreadyExistingUserException;
import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.model.user.User;
import com.codecool.puzzleshowdown.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(UserRegistrationDTO userRegistration){
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

    public boolean userValidation(UserLoginDTO userLoginDTO){
        try{
            Optional<User> optionalUser;
            boolean isEmail = emailValidator(userLoginDTO.authenticator());

            getUserInfo(userLoginDTO, isEmail);
            return true;

        } catch (Exception e){
            throw new RuntimeException();
        }
    }

    private void getUserInfo(UserLoginDTO userLoginDTO, boolean isEmail) {
        Optional<User> optionalUser;
        if(isEmail){
            optionalUser = userRepository.findByEmail(userLoginDTO.authenticator());
        } else {
            optionalUser = userRepository.findByUserName(userLoginDTO.authenticator());
        }
    }

    private boolean emailValidator(String userAuthenticator){
        String emailRegex = "^(.+)@(\\S+) $.";
        return Pattern.compile(emailRegex).matcher(userAuthenticator).matches();
    }
}
