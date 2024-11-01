package com.example.application.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.server.VaadinSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

public abstract class SecuredView extends VerticalLayout implements BeforeEnterObserver {

    private static final Logger LOGGER = LogManager.getLogger(SecuredView.class);

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {

            String email = (String) VaadinSession.getCurrent().getAttribute("email");

            if (email != null) {

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(email, null, Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authToken);

                LOGGER.info("Restored authentication for user: {}", email);

            } else {

                LOGGER.info("No authenticated user found in VaadinSession. Redirecting to login.");
                event.forwardTo("login");

            }
        } else {

            LOGGER.info("User is already authenticated: {}", authentication.getName());
        }
    }
}

