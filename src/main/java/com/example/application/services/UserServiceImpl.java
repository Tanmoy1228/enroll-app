package com.example.application.services;


import com.example.application.dto.UserDto;
import com.example.application.entity.User;
import com.example.application.exceptions.UserException;
import com.example.application.repository.UserRepository;
import com.example.application.utils.TranslationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(UserDto userDto) {

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isPresent()) {
            throw new UserException(TranslationUtils.getTranslation("exception.email-already-exist"));
        }

         User newUserToSave = new User(
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword())
        );

        try {

            userRepository.save(newUserToSave);

            LOGGER.info("Registration success.");

        } catch (Exception e) {

            LOGGER.error("Registration error : ", e);

            throw e;
        }
    }

    @Override
    public UserDto getUserByEmail(String email) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()) {
            throw new UserException(TranslationUtils.getTranslation("exception.user-not-found"));
        }

        return new UserDto(user.get().getEmail(), user.get().getPassword(), user.get().getStatus());
    }

    @Override
    public void updateUser(UserDto userDto) {

        Optional<User> user = userRepository.findByEmail(userDto.getEmail());

        if (user.isEmpty()) {
            throw new UserException(TranslationUtils.getTranslation("exception.email-not-found"));
        }

        user.get().setStatus(userDto.getStatus());

        try {

            userRepository.save(user.get());

            LOGGER.info("User update success.");

        } catch (Exception e) {

            LOGGER.error("User update : ", e);

            throw e;
        }

    }
}
