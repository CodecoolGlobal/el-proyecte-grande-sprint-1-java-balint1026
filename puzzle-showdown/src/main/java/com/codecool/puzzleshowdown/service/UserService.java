package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.custom_exception.NonExistingUserException;
import com.codecool.puzzleshowdown.custom_exception.NullValueException;
import com.codecool.puzzleshowdown.dto.user.*;
import com.codecool.puzzleshowdown.custom_exception.*;
import com.codecool.puzzleshowdown.repository.model.Puzzle;
import com.codecool.puzzleshowdown.repository.model.Role;
import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PuzzleService puzzleService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PuzzleService puzzleService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.puzzleService = puzzleService;
        this.encoder = encoder;
    }

    public UserLoginResponseDTO saveUser(NewUserDTO newUserDTO) {
        try {
            if (!emailValidator(newUserDTO.email())) {
                throw new InvalidEmailFormatException();
            }
            User user = new User(
                    newUserDTO.username(),
                    newUserDTO.email(),
                    encoder.encode(newUserDTO.password()),
                    Role.USER
            );

            var newUser = userRepository.save(user);
            return new UserLoginResponseDTO(
                    newUser.getId(),
                    newUser.username(),
                    newUser.getPassword(),
                    newUser.getImage()
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if (e.getMessage().contains("duplicate key value violates unique constraint \"email_unique\"")) {
                throw new AlreadyExistingUserEmailException(newUserDTO.email());
            } else if (e.getMessage().contains("duplicate key value violates unique constraint \"user_name_unique")) {
                throw new AlreadyExistingUserNameException(newUserDTO.username());
            }
            throw new NullValueException();
        }

    }

    public List<UserLeaderBoard> getUsersByRoleSorted() {
        List<UserLeaderBoard> usersByRating = userRepository.findByOrderByRatingDescUserNameAsc().stream()
                .map(user -> new UserLeaderBoard(user.getRating(), user.username(), user.getImage()))
                .toList();
        return usersByRating;
    }

    private boolean emailValidator(String userAuthenticator) {
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
        if (!user.getSolvedPuzzles().contains(puzzle)) {
            user.getSolvedPuzzles().add(puzzle);
            userRepository.save(user);
        }
    }

    public UserDTO getUserById(long id) {
        User user = getUser(id);
        return new UserDTO(user.getId(), user.username(), user.getPassword(), user.getSolvedPuzzles());
    }
}
