package com.example.application.views;

import com.example.application.dto.UserDto;
import com.example.application.services.UserService;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfileLayout extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(ProfileLayout.class);

    public ProfileLayout(UserService userService) {

        String email = (String) VaadinSession.getCurrent().getAttribute("email");
        String status = (String) VaadinSession.getCurrent().getAttribute("status");

        if (status == null && email != null) {

            UserDto userDto = userService.getUserByEmail(email);
            status = userDto.getStatus();

            VaadinSession.getCurrent().setAttribute("status", status);
        }

        Span emailDiv = new Span("Email: " + (email != null ? email : "Unknown Email"));
        emailDiv.getStyle().set("background-color", "white").set("color", "blue");

        Span statusDiv = new Span("Status: " + (status != null ? status : "NOT APPLIED"));
        statusDiv.getStyle().set("background-color", "white").set("color", "red");

        setSpacing(true);
        setPadding(false);
        getStyle()
                .set("padding", "10px")
                .set("margin-top", "5px")
                .set("border-radius", "5px")
                .set("background-color", "#005b9a");

        add(emailDiv, statusDiv);
    }
}
