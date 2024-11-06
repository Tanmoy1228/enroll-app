package com.example.application.services;

import com.example.application.entity.Contact;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.ContactRepository;
import com.example.application.utils.TranslationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    private final Logger LOGGER = LogManager.getLogger(ContactServiceImpl.class);

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public void save(Contact contact) {

        try {
            contactRepository.save(contact);
        } catch (Exception e) {
            LOGGER.error("Failed to save contact. {}", e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }

    public Contact findContactById(Long id) {

        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.contact-not-found"));
        }

        return contact.get();
    }

    public List<Contact> getAllContacts(String email) {
        return contactRepository.findAllByEmail(email);
    }

    public void deleteContact(Long id) {

        Optional<Contact> contact = contactRepository.findById(id);

        if (contact.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.contact-not-found"));
        }

        try {
            contactRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to delete contact. {}", e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}

