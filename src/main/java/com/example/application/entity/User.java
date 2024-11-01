package com.example.application.entity;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable=false, unique = true)
    private String email;

    @Column(name = "password", columnDefinition = "VARCHAR(1000)", nullable=false)
    private String password;

    @Column(name = "status", columnDefinition = "VARCHAR(20)", nullable=false)
    private String status;

    public User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.status = "NOT APPLIED";
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

    public void setEmail(String username) {
        this.email = username;
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
