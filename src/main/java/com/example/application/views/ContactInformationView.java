package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AnonymousAllowed
@PageTitle("Contact Info")
@Route(value = "contact-information", layout = MainLayout.class)
public class ContactInformationView extends SecuredView {

    private static final Logger LOGGER = LogManager.getLogger(ContactInformationView.class);

    private final ContactComponent contactComponent;
    private final AddressComponent registrationAddress;
    private final AddressComponent currentAddress;
    private final AddressComponent placeOfBirthAddress;
    private final AddressComponentFactory addressFactory;

    private final Button nextPageButton = new Button(TranslationUtils.getTranslation("button.label.next-page"));

    public ContactInformationView(ContactComponent contactComponent, AddressComponentFactory addressFactory) {

        this.contactComponent = contactComponent;
        this.addressFactory = addressFactory;

        setSpacing(true);
        setPadding(true);
        setSizeFull();

        registrationAddress = addressFactory.createAddressComponent(AddressType.REGISTRATION_ADDRESS);
        currentAddress = addressFactory.createAddressComponent(AddressType.CURRENT_ADDRESS);
        placeOfBirthAddress = addressFactory.createAddressComponent(AddressType.BIRTH_ADDRESS);

        configureNextPageButton();

        add(contactComponent, registrationAddress, currentAddress, placeOfBirthAddress, nextPageButton);
    }

    private void configureNextPageButton() {

        nextPageButton.getStyle()
                .set("width", "300px")
                .set("height", "50px")
                .set("color", "white")
                .set("margin-left", "200px")
                .set("margin-bottom", "200px")
                .set("background-color", "#007bff");
    }
}

