package com.arhimisha.intech.services;

import com.arhimisha.intech.domain.Authority;
import com.arhimisha.intech.domain.User;
import com.arhimisha.intech.registration.RegistrationDetails;
import com.arhimisha.intech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Username Not Found");
        }
        return user.get();
    }

    public Optional<User> findUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public void registrationUser(RegistrationDetails registrationDetails) {
        final String username = registrationDetails.getUsername();
        final String password = registrationDetails.getPassword();
        final String email = registrationDetails.getEmail();

        if (username == null || username.isBlank()) {
            throw new RuntimeException("Login is empty");
        }
        if (password == null || password.isBlank()) {
            throw new RuntimeException("Password is empty");
        }
        if (email == null || email.isBlank()){
            throw new RuntimeException("Email is empty");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User with username " + username + " is already exist");
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email " + email + " is already exist");
        }

        if (!password.equals(registrationDetails.getConform())) {
            throw new RuntimeException("Password doesn't match");
        }

        User user = new User(
                0L,
                email,
                username,
                passwordEncoder.encode(password),
                registrationDetails.getFirstName(),
                registrationDetails.getLastName(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
        user.setAuthorities(new ArrayList<>(Collections.singletonList(new Authority(0L, "ROLE_USER", user))));
        userRepository.save(user);
    }
}
