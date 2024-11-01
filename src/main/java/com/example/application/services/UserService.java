package com.example.application.services;

import com.example.application.dto.UserDto;

public interface UserService {

    void registerUser(UserDto userDto) throws Exception;

    UserDto getUserByEmail(String email);

}
