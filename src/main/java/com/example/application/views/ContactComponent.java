package com.example.application.views;

import com.example.application.dto.ContactType;
import com.example.application.services.ContactService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.example.application.entity.Contact;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

@UIScope
@SpringComponent
public class ContactComponent extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(ContactComponent.class);

    private final Grid<Contact> contactsGrid = new Grid<>(Contact.class, false);
    private final Button addContactButton = new Button(TranslationUtils.getTranslation("contactInfo.button.addContact"));

    private final Dialog addContactDialog = new Dialog();
    private final ComboBox<ContactType> typeField = new ComboBox<>(TranslationUtils.getTranslation("contactInfo.label.type"));
    private final TextField contactField = new TextField(TranslationUtils.getTranslation("contactInfo.label.contacts"));
    private final Button saveContactButton = new Button(TranslationUtils.getTranslation("contactInfo.button.add"));

    private Contact currentContact = null;
    private final ContactService contactService;
    private List<Contact> contacts = new ArrayList<>();
    private final Binder<Contact> binder = new Binder<>(Contact.class);

    public ContactComponent(ContactService contactService) {

        this.contactService = contactService;

        setSpacing(true);
        setPadding(true);
        setWidth("600px");

        configureContactsGrid();
        configureAddContactButton();
        configureAddContactDialog();

        add(new H3(TranslationUtils.getTranslation("contactInfo.title")), contactsGrid, addContactButton);

        addAttachListener(event -> initializeData());
    }

    private void configureContactsGrid() {
        contactsGrid.addColumn(contactType -> contactType.getType().getCode()).setHeader(TranslationUtils.getTranslation("contactInfo.label.type"));
        contactsGrid.addColumn(Contact::getContact).setHeader(TranslationUtils.getTranslation("contactInfo.label.contacts"));
        contactsGrid.addComponentColumn(this::createActionButtons).setHeader(TranslationUtils.getTranslation("contactInfo.label.actions"));
    }

    private void configureAddContactButton() {
        addContactButton.getStyle().set("margin-left", "auto");
        addContactButton.addClickListener(event -> openAddContactDialog());
    }

    private void openAddContactDialog() {

        currentContact = new Contact();
        binder.readBean(currentContact);

        typeField.clear();
        contactField.clear();
        addContactDialog.open();
    }

    private void configureAddContactDialog() {

        addContactDialog.setWidth("550px");
        addContactDialog.setHeight("150px");

        typeField.setItems(ContactType.values());
        typeField.setItemLabelGenerator(ContactType::getCode);
        saveContactButton.addClickListener(event -> handleSaveContact());

        HorizontalLayout dialogLayout = new HorizontalLayout(typeField, contactField, saveContactButton);

        addContactDialog.add(dialogLayout);
        dialogLayout.setAlignItems(Alignment.BASELINE);
    }

    private void handleSaveContact() {

        if(checkDuplicateContact()) {
            Notification.show(TranslationUtils.getTranslation("notification.duplicateContact"), 1000, Notification.Position.TOP_CENTER);
            return;
        }

        if (binder.writeBeanIfValid(currentContact)) {

            currentContact.setEmail((String) VaadinSession.getCurrent().getAttribute("email"));

            try {
                contactService.save(currentContact);

                if (!contacts.contains(currentContact)) {
                    contacts.add(currentContact);
                } else {
                    contactsGrid.getDataProvider().refreshItem(currentContact);
                }
                Notification.show(TranslationUtils.getTranslation("notification.dataSavedSuccessfully"), 1000, Notification.Position.TOP_CENTER);

            } catch (Exception e) {
                Notification.show(TranslationUtils.getTranslation("notification.contactNotSaved"), 1000, Notification.Position.TOP_CENTER);
            }

            setContacts(contacts);

            typeField.clear();
            contactField.clear();
            addContactDialog.close();
        } else {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 1000, Notification.Position.TOP_CENTER);
        }
    }

    private HorizontalLayout createActionButtons(Contact contact) {
        Button editButton = new Button("Edit", event -> editContact(contact));
        Button deleteButton = new Button("Delete", event -> deleteContact(contact));
        return new HorizontalLayout(editButton, deleteButton);
    }

    private void editContact(Contact contact) {

        currentContact = contact;
        binder.readBean(currentContact);

        typeField.setValue(contact.getType());
        contactField.setValue(contact.getContact());
        addContactDialog.open();
    }

    private void deleteContact(Contact contact) {

        try {
            contactService.deleteContact(contact.getId());
            contacts.remove(contact);
            setContacts(contacts);
        } catch (Exception e) {
            Notification.show(TranslationUtils.getTranslation("notification.contactNotDelete"), 1000, Notification.Position.TOP_CENTER);
        }
    }

    public void setContacts(List<Contact> contacts) {

        int rowHeight = 55;
        int baseHeight = 60;
        int contactCount = 0;

        if (contacts != null) {
            contactCount = Math.min(10, contacts.size());
        }

        int calculatedHeight = baseHeight + (contactCount * rowHeight);

        String gridHeight = calculatedHeight + "px";
        contactsGrid.setHeight(gridHeight);

        if (contacts == null || contacts.isEmpty()) {
            return;
        }

        contactsGrid.setItems(contacts);
    }

    private void initializeData() {

        loadContacts();
        configureBinder();
    }

    private void loadContacts() {

        String email = (String) VaadinSession.getCurrent().getAttribute("email");

        contacts = contactService.getAllContacts(email);
        setContacts(contacts);
    }

    private boolean checkDuplicateContact() {

        for (Contact contact : contacts) {
            if (!contact.equals(currentContact) && contact.getContact().trim().equals(currentContact.getContact().trim())) {
                return true;
            }
        }

        return false;
    }

    private void configureBinder() {

        binder.forField(typeField)
                .asRequired(TranslationUtils.getTranslation("validation.required.type"))
                .bind(Contact::getType, Contact::setType);

        binder.forField(contactField)
                .asRequired(TranslationUtils.getTranslation("validation.required.contact"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.contact"), 10, 50))
                .bind(Contact::getContact, Contact::setContact);
    }
}
