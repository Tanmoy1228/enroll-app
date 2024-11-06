package com.example.application.views;

import com.example.application.dto.ContactType;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class ContactComponent extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(ContactComponent.class);

    private final Grid<Contact> contactsGrid = new Grid<>(Contact.class, false);
    private final Button addContactButton = new Button(TranslationUtils.getTranslation("contactInfo.button.addContact"));

    private final Dialog addContactDialog = new Dialog();
    private final ComboBox<ContactType> typeField = new ComboBox<>(TranslationUtils.getTranslation("contactInfo.label.type"));
    private final TextField contactField = new TextField(TranslationUtils.getTranslation("contactInfo.label.contacts"));
    private final Button saveContactButton = new Button(TranslationUtils.getTranslation("contactInfo.button.add"));

    private final List<Contact> contacts = new ArrayList<>();

    public ContactComponent() {

        setSpacing(true);
        setPadding(true);
        setWidth("600px");

        configureContactsGrid();
        configureAddContactButton();
        configureAddContactDialog();

        add(new H3(TranslationUtils.getTranslation("contactInfo.title")), contactsGrid, addContactButton);
        setContacts(new ArrayList<>());
    }

    private void configureContactsGrid() {
        contactsGrid.addColumn(contactType -> contactType.getType().getCode()).setHeader(TranslationUtils.getTranslation("contactInfo.label.type"));
        contactsGrid.addColumn(Contact::getContact).setHeader(TranslationUtils.getTranslation("contactInfo.label.contacts"));
        contactsGrid.addComponentColumn(this::createActionButtons).setHeader(TranslationUtils.getTranslation("contactInfo.label.actions"));
    }

    private void configureAddContactButton() {
        addContactButton.getStyle().set("margin-left", "auto");
        addContactButton.addClickListener(event -> addContactDialog.open());
    }

    private void configureAddContactDialog() {

        addContactDialog.setWidth("550px");
        addContactDialog.setHeight("150px");

        typeField.setItems(ContactType.values());
        typeField.setItemLabelGenerator(ContactType::getCode);
        saveContactButton.addClickListener(event -> handleSaveContact(typeField, contactField));

        HorizontalLayout dialogLayout = new HorizontalLayout(typeField, contactField, saveContactButton);

        addContactDialog.add(dialogLayout);
        dialogLayout.setAlignItems(Alignment.END);
    }

    private void handleSaveContact(ComboBox<ContactType> typeField, TextField contactField) {

        ContactType type = typeField.getValue();
        String contactValue = contactField.getValue();

        if (type != null && !contactValue.isEmpty()) {

            Contact newContact = new Contact(type, contactValue);
            contacts.add(newContact);
            setContacts(contacts);

            typeField.clear();
            contactField.clear();
            addContactDialog.close();
        } else {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"));
        }
    }

    private HorizontalLayout createActionButtons(Contact contact) {
        Button editButton = new Button("Edit", event -> editContact(contact));
        Button deleteButton = new Button("Delete", event -> deleteContact(contact));
        return new HorizontalLayout(editButton, deleteButton);
    }

    private void editContact(Contact contact) {
        Notification.show("Edit Clicked for: " + contact.getContact());
    }

    private void deleteContact(Contact contact) {
        contacts.remove(contact);
        setContacts(contacts);
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
}
