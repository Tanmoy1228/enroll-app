package com.example.application.entity;

import com.example.application.dto.ContactType;
import jakarta.persistence.*;

@Entity
@Table(name = "contacts")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(100)", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", columnDefinition = "VARCHAR(20)", nullable = false)
    private ContactType type;

    @Column(name = "contact", columnDefinition = "VARCHAR(100)", nullable = false)
    private String contact;

    public Contact() {}

    public Contact(ContactType type, String contact, String email) {
        this.email = email;
        this.type = type;
        this.contact = contact;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ContactType getType() { return type; }

    public void setType(ContactType type) { this.type = type; }

    public String getContact() { return contact; }

    public void setContact(String contact) { this.contact = contact; }
}
