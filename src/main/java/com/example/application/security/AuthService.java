package com.example.application.security;

import com.example.application.entity.User;
import com.example.application.exceptions.UserException;
import com.example.application.repository.UserRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UserException(TranslationUtils.getTranslation("exception.email-not-found"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(), user.get().getPassword(), new ArrayList<>()
        );
    }

    public boolean isAuthenticatedUser(String username, String password) {

        UserDetails userDetails = loadUserByUsername(username);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities())
            );

            return true;
        }

        return false;
    }
}
