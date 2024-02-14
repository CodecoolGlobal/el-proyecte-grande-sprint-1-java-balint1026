package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.AlreadyExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.InvalidEmailFormatException;
import com.codecool.puzzleshowdown.custom_exception.NonExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.NullValueException;
import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserLoginResponseDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.repository.UserRepository;
import org.hibernate.PropertyValueException;
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

    public UserLoginResponseDTO saveUser(UserRegistrationDTO userRegistration){
        try{
            if(!emailValidator(userRegistration.email())){
                throw new InvalidEmailFormatException();
            }
            User user = new User(
                    userRegistration.firstName(),
                    userRegistration.lastName(),
                    userRegistration.userName(),
                    userRegistration.email(),
                    userRegistration.password()
            );

            userRepository.save(user);
            UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO(user.getUserName(), user.getImage());
            return userLoginResponseDTO;
        } catch (Exception e){
            if (e.getMessage().contains("duplicate key value")){
                throw new AlreadyExistingUserException(userRegistration.email());
            } else if(e.getMessage().contains("email")){
                throw e;
            }
            throw new NullValueException();
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

    public Optional<User> getUserData(String authenticator) {
        boolean isEmail = emailValidator(authenticator);
        if(isEmail){
            return userRepository.findByEmail(authenticator);
        } else {
            return userRepository.findByUserName(authenticator);
        }
    }

    private boolean emailValidator(String userAuthenticator){
        String emailRegex = ".+\\@.+\\..+";
        return Pattern.compile(emailRegex).matcher(userAuthenticator).matches();
    }
}
