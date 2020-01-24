package com.arhimisha.intech.services;

import com.arhimisha.intech.domain.Authority;
import com.arhimisha.intech.domain.User;
import com.arhimisha.intech.registration.RegistrationDetails;
import com.arhimisha.intech.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

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
        try {
            return userRepository.findByUsername(username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("Username Not Found");
        }
    }

    public void registrationUser(RegistrationDetails registrationDetails) {
        if (userRepository.findByUsername(registrationDetails.getUsername()) != null) {
            throw new RuntimeException("User with username " + registrationDetails.getUsername() + " is already exist");
        }
        if (userRepository.findByEmail(registrationDetails.getEmail()) != null) {
            throw new RuntimeException("User with email " + registrationDetails.getEmail() + " is already exist");
        }

        if ( !registrationDetails.getPassword().equals(registrationDetails.getConform())){
            throw new RuntimeException("Password doesn't match");
        }

        User user = new User (
                0L,
                registrationDetails.getEmail(),
                registrationDetails.getUsername(),
                passwordEncoder.encode(registrationDetails.getPassword()),
                "",
                "",
                true,
                true,
                true,
                true,
                new ArrayList<Authority>());
        userRepository.save(user);
    }
}
