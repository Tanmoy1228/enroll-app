package com.example.application.dto;

public class UserDto {

    private Long id;

    private String email;

    private String password;

    private String status;

    public UserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserDto(String email, String password, String status) {
        this.email = email;
        this.status = status;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
