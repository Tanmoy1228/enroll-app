package com.example.application.services;

import com.example.application.entity.Contact;

import java.util.List;

public interface ContactService {

    void save(Contact contact);

    Contact findContactById(Long id);

    List<Contact> getAllContacts(String email);

    void deleteContact(Long id);

}
