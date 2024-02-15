package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.*;
import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserLoginResponseDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.repository.model.User;
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
            System.out.println(e.getMessage());
            if (e.getMessage().contains("duplicate key value violates unique constraint \"email_unique\"")){
                throw new AlreadyExistingUserEmailException(userRegistration.email());
            } else if(e.getMessage().contains("duplicate key value violates unique constraint \"user_name_unique")){
                throw new AlreadyExistingUserNameException(userRegistration.userName());
            }
            throw new NullValueException();
        }

    }

    public UserLoginResponseDTO userValidation(UserLoginDTO userLoginDTO){
        Optional<User> optionalUser = getUserData(userLoginDTO.authenticator());
        if(optionalUser.isEmpty()){
            throw new NonExistingUserException(userLoginDTO.authenticator());
        }

        User searchedUser = optionalUser.get();
        if(userLoginDTO.password().equals(searchedUser.getPassword())){
            return new UserLoginResponseDTO(searchedUser.getUserName(), searchedUser.getImage());
        }
        throw new IncorrectPasswordException();
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
