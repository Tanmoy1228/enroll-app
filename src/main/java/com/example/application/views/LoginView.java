package com.example.application.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldBase;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

@AnonymousAllowed
@Route(value = "")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(LoginView.class);

    private final TextField loginEmail;
    private final PasswordField loginPassword;

    private final TextField registerEmail;
    private final PasswordField registerPassword;
    private final PasswordField registerRetypePassword;

    private final Tabs tabs;
    private final Tab loginTab;
    private final Tab registerTab;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"
    );

    public LoginView() {

        loginEmail = createTextField("Email");
        loginPassword = createPasswordField("Password");

        registerEmail = createTextField("Email");
        registerPassword = createPasswordField("Password");
        registerRetypePassword = createPasswordField("Retype Password");

        HorizontalLayout loginLayout = new HorizontalLayout(configureLoginForm());
        HorizontalLayout registerLayout = new HorizontalLayout(configureRegistrationForm());

        loginLayout.setVisible(true);
        registerLayout.setVisible(false);

        loginTab = new Tab("Login");
        registerTab = new Tab("Registration");

        tabs = createTabs();
        configureTabs(loginLayout, registerLayout);

        setupMainLayout();
        add(tabs, loginLayout, registerLayout);
    }

    private TextField createTextField(String label) {

        TextField textField = new TextField(label);
        textField.setWidthFull();
        textField.setRequired(true);

        return textField;
    }

    private PasswordField createPasswordField(String label) {

        PasswordField passwordField = new PasswordField(label);
        passwordField.setWidthFull();
        passwordField.setRequired(true);

        return passwordField;
    }

    private Tabs createTabs() {
        Tabs tabs = new Tabs(loginTab, registerTab);
        tabs.getStyle().set("margin-top", "100px");
        return tabs;
    }

    private void configureTabs(HorizontalLayout loginLayout, HorizontalLayout registerLayout) {
        tabs.addSelectedChangeListener(event -> {
            loginLayout.setVisible(tabs.getSelectedTab() == loginTab);
            registerLayout.setVisible(tabs.getSelectedTab() == registerTab);
        });
    }

    private void setupMainLayout() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.START);
        getStyle().set("position", "relative");
    }

    private VerticalLayout configureLoginForm() {

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setWidth("400px");

        Span loginTitle = createTitle("Please Login");
        Button loginButton = createButton("Login", this::login);
        Span forgotPasswordLink = forgotPasswordLink();

        loginLayout.add(loginTitle, loginEmail, loginPassword, loginButton, forgotPasswordLink);

        return loginLayout;
    }

    private VerticalLayout configureRegistrationForm() {

        VerticalLayout registerLayout = new VerticalLayout();
        registerLayout.setWidth("400px");

        Span registerTitle = createTitle("Please Register");
        Button registerButton = createButton("Register", this::register);

        registerLayout.add(registerTitle, registerEmail, registerPassword, registerRetypePassword, registerButton);

        return registerLayout;
    }

    private Span createTitle(String text) {
        Span title = new Span(text);
        title.getStyle().set("font-size", "24px").set("font-weight", "bold").set("margin-bottom", "20px");
        return title;
    }

    private Button createButton(String text, Runnable action) {

        Button button = new Button(text, event -> action.run());
        button.setWidthFull();
        button.addClickShortcut(Key.ENTER);
        button.getStyle()
                .set("color", "white")
                .set("cursor", "pointer")
                .set("font-weight", "bold")
                .set("background-color", "#007bff");

        return button;
    }

    private Span forgotPasswordLink() {

        Span forgotPasswordLink = new Span("Can't access my account");

        forgotPasswordLink.getStyle()
                .set("color", "#007bff")
                .set("cursor", "pointer")
                .set("margin-left", "auto");

        return forgotPasswordLink;
    }

    private void login() {

        if (!isFieldValid(loginEmail, "Email") || !isEmailValid(loginEmail) || !isFieldValid(loginPassword, "Password")) {
            return;
        }

        LOGGER.info("Login button clicked");
    }

    private void register() {

        if (!isFieldValid(registerEmail, "Email") || !isEmailValid(registerEmail) ||
                !isFieldValid(registerPassword, "Password") || !isFieldValid(registerRetypePassword, "Password")) {
            return;
        }

        LOGGER.info("Register button clicked");
    }

    private boolean isEmailValid(TextField field) {

        if (EMAIL_PATTERN.matcher(field.getValue()).matches()) {
            return true;
        }

        field.focus();
        field.setInvalid(true);
        field.getElement().callJsFunction("scrollIntoView");
        Notification.show("Please provide a valid email.", 3000, Notification.Position.TOP_CENTER);

        return false;
    }

    private boolean isFieldValid(TextFieldBase<?, String> field, String label) {

        if (field.isEmpty()) {

            field.focus();
            field.setInvalid(true);
            field.getElement().callJsFunction("scrollIntoView");
            Notification.show(label + " cannot be empty", 3000, Notification.Position.TOP_CENTER);

            return false;
        }

        field.setInvalid(false);
        return true;
    }
}

