package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Relatives Info")
@Route(value = "relatives-information", layout = MainLayout.class)
public class RelativesInformationView extends SecuredView {

    public RelativesInformationView() {
        add(new H1("Relatives Information View"));
    }
}
