package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.AlreadyExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.NonExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.NullValueException;
import com.codecool.puzzleshowdown.dto.user.UserDTO;
import com.codecool.puzzleshowdown.custom_exception.*;
import com.codecool.puzzleshowdown.dto.user.UserLoginDTO;
import com.codecool.puzzleshowdown.dto.user.UserLoginResponseDTO;
import com.codecool.puzzleshowdown.dto.user.UserRegistrationDTO;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PuzzleService puzzleService;

    @Autowired
    public UserService(UserRepository userRepository, PuzzleService puzzleService) {
        this.userRepository = userRepository;
        this.puzzleService = puzzleService;
    }

    public UserLoginResponseDTO saveUser(UserRegistrationDTO userRegistration){
        try{
            if(!emailValidator(userRegistration.email())){
                throw new InvalidEmailFormatException();
            }
            User user = new User(
                    "",
                    "",
                    userRegistration.userName(),
                    userRegistration.email(),
                    userRegistration.password()
            );

            userRepository.save(user);
            return new UserLoginResponseDTO(
                    user.getId(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getImage());
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

        User user = optionalUser.get();
        if(userLoginDTO.password().equals(user.getPassword())){
            return new UserLoginResponseDTO(user.getId(), user.getUserName(), user.getPassword(), user.getImage());
        }
        return null;
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

    public User getUser(long id) {
        Optional<User> respond = userRepository.findById(id);
        return respond.orElse(null);
    }
    public User getUser(String userName) {
        Optional<User> respond = userRepository.findByUserName(userName);
        return respond.orElse(null);
    }

    public boolean patchRating(long userId, int rating) {
        return userRepository.updateRating(userId, rating);
    }

    public void savePuzzleToUser(long userId, String puzzleId) {
        User user = getUser(userId);
        Puzzle puzzle = puzzleService.getPuzzleById(puzzleId);
        if (!user.getSolvedPuzzles().contains(puzzle)){
            user.getSolvedPuzzles().add(puzzle);
            userRepository.save(user);
        }
    }
    public UserDTO getUserById(long id){
        User user = getUser(id);
        return new UserDTO(user.getId(), user.getUserName(), user.getPassword(), user.getSolvedPuzzles());
    }
}
