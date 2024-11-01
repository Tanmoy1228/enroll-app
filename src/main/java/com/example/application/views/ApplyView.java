package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Apply")
@Route(value = "apply", layout = MainLayout.class)
public class ApplyView extends SecuredView {

    public ApplyView() {
        add(new H1("Apply View"));
    }
}
