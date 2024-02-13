package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.AlreadyExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.NonExistingUserException;
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
        Optional<User> optionalUser = getUserData(userLoginDTO.authenticator());
        if(optionalUser.isEmpty()){
            throw new NonExistingUserException(userLoginDTO.authenticator());
        }

        User searchedUser = optionalUser.get();
        if(userLoginDTO.password().equals(searchedUser.getPassword())){
            return true;
        }
        return false;
    }

    private Optional<User> getUserData(String authenticator) {
        boolean isEmail = emailValidator(authenticator);
        if(isEmail){
            return userRepository.findByEmail(authenticator);
        } else {
            return userRepository.findByUserName(authenticator);
        }
    }

    private boolean emailValidator(String userAuthenticator){
        String emailRegex = "^(.+)@(\\S+) $.";
        return Pattern.compile(emailRegex).matcher(userAuthenticator).matches();
    }
}
