package com.codecool.puzzleshowdown.service;

import com.codecool.puzzleshowdown.repository.UserRepository;
import com.codecool.puzzleshowdown.repository.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> optionalUserEntity = userRepository.findByUserName(username);
        User user = null;
        if (optionalUserEntity.isPresent()) user = optionalUserEntity.get();
        assert user != null;
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(user.getRole().toString());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Set.of(role));
    }
}
