package com.example.application.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@AnonymousAllowed
@PageTitle("Education Info")
@Route(value = "education-information", layout = MainLayout.class)
public class EducationInformationView extends SecuredView {

    public EducationInformationView() {
        add(new H1("Education Information View"));
    }
}
