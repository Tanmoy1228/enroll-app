package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@Route(value = "basic-information", layout = MainLayout.class)
@PageTitle("Basic Info")
public class BasicInformationView extends SecuredView {

    public BasicInformationView() {

        add(new H1("Basic Information"));

    }
}

