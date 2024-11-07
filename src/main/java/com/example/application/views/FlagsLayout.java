package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.dto.ContactType;
import com.example.application.dto.RelativeType;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Locale;

@UIScope
@Component
public class FlagsLayout extends HorizontalLayout {

    private static final Logger LOGGER = LogManager.getLogger(FlagsLayout.class);

    public FlagsLayout() {

        Image russianFlag = createFlagImage("images/russia_flag.png", "Русский", "ru");
        Image englishFlag = createFlagImage("images/england_flag.png", "English", "en");
        Image kyrgyzFlag = createFlagImage("images/kyrgyz_flag.png", "Кыргыз тили", "ky");

        setSpacing(true);
        setAlignItems(Alignment.CENTER);

        add(kyrgyzFlag, russianFlag, englishFlag);
    }

    private Image createFlagImage(String imageLocation, String label, String language) {

        Image flagImage = new Image(imageLocation, label);

        flagImage.getElement().setAttribute("title", label);
        flagImage.addClickListener(event -> changeLanguage(new Locale(language)));

        flagImage.setWidth("24px");
        flagImage.setHeight("16px");
        flagImage.getStyle().set("cursor", "pointer");

        return flagImage;
    }

    private void changeLanguage(Locale locale) {

        LOGGER.info("Language changed to {}", locale);

        VaadinSession.getCurrent().setLocale(locale);

        for (AddressType addressType : AddressType.values()) {
            addressType.refreshCode();
        }

        for (ContactType contactType : ContactType.values()) {
            contactType.refreshCode();
        }

        for (RelativeType relativeType : RelativeType.values()) {
            relativeType.refreshCode();
        }

        getUI().ifPresent(ui -> ui.getPage().reload());
    }
}
