package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Contact Info")
@Route(value = "contact-information", layout = MainLayout.class)
public class ContactInformationView extends SecuredView {

    public ContactInformationView() {
        add(new H1("Contact Information View"));
    }
}

