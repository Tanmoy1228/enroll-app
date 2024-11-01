package com.example.application.views;

import com.example.application.dto.UserDto;
import com.example.application.exceptions.UserException;
import com.example.application.security.AuthService;
import com.example.application.services.UserService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

@AnonymousAllowed
@Route(value = "login")
@PageTitle("Login")
public class LoginView extends VerticalLayout {

    private static final Logger LOGGER = LogManager.getLogger(LoginView.class);

    private final AuthService authService;
    private final UserService userService;
    private final FlagsLayout flagsLayout;

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

    public LoginView(AuthService authService, UserService userService, FlagsLayout flagsLayout) {

        this.authService = authService;
        this.userService = userService;
        this.flagsLayout = flagsLayout;

        loginEmail = createTextField(TranslationUtils.getTranslation("login.email"));
        loginPassword = createPasswordField(TranslationUtils.getTranslation("login.password"));

        registerEmail = createTextField(TranslationUtils.getTranslation("register.email"));
        registerPassword = createPasswordField(TranslationUtils.getTranslation("register.password"));
        registerRetypePassword = createPasswordField(TranslationUtils.getTranslation("register.retype-password"));

        HorizontalLayout loginLayout = new HorizontalLayout(configureLoginForm());
        HorizontalLayout registerLayout = new HorizontalLayout(configureRegistrationForm());

        loginLayout.setVisible(true);
        registerLayout.setVisible(false);

        loginTab = new Tab(TranslationUtils.getTranslation("login.tab"));
        registerTab = new Tab(TranslationUtils.getTranslation("register.tab"));

        tabs = createTabs();
        configureTabs(loginLayout, registerLayout);

        setupMainLayout();
        setupFlagsLayout();

        add(tabs, loginLayout, registerLayout, this.flagsLayout);
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

    private void setupFlagsLayout() {

        flagsLayout.setWidthFull();
        flagsLayout.setPadding(true);
        flagsLayout.setJustifyContentMode(JustifyContentMode.END);
        flagsLayout.getStyle().set("position", "absolute").set("top", "0").set("right", "0");
    }

    private VerticalLayout configureLoginForm() {

        VerticalLayout loginLayout = new VerticalLayout();
        loginLayout.setWidth("400px");

        Span loginTitle = createTitle(TranslationUtils.getTranslation("login.title"));
        Button loginButton = createButton(TranslationUtils.getTranslation("login.button"), this::login);
        Span forgotPasswordLink = forgotPasswordLink();

        loginLayout.add(loginTitle, loginEmail, loginPassword, loginButton, forgotPasswordLink);

        return loginLayout;
    }

    private VerticalLayout configureRegistrationForm() {

        VerticalLayout registerLayout = new VerticalLayout();
        registerLayout.setWidth("400px");

        Span registerTitle = createTitle(TranslationUtils.getTranslation("register.title"));
        Button registerButton = createButton(TranslationUtils.getTranslation("register.button"), this::register);

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

        Span forgotPasswordLink = new Span(TranslationUtils.getTranslation("login.forgot-password"));

        forgotPasswordLink.getStyle()
                .set("color", "#007bff")
                .set("cursor", "pointer")
                .set("margin-left", "auto");

        return forgotPasswordLink;
    }

    private void login() {

        if (!isFieldValid(loginEmail, TranslationUtils.getTranslation("login.email")) || !isEmailValid(loginEmail) ||
                !isFieldValid(loginPassword, TranslationUtils.getTranslation("login.password"))) {
            return;
        }

        String email = loginEmail.getValue();
        String password = loginPassword.getValue();

        try {

            boolean loginSuccessful = authService.isAuthenticatedUser(email, password);

            if (loginSuccessful) {

                Notification.show(TranslationUtils.getTranslation("login.success-message"), 1000, Notification.Position.TOP_CENTER);

                VaadinSession.getCurrent().setAttribute("email", email);
                VaadinSession.getCurrent().setAttribute("selectedTabIndex", 0);

                LOGGER.info("Logged in successfully. Email={}", VaadinSession.getCurrent().getAttribute("email"));

                UI.getCurrent().navigate(BasicInformationView.class);

            } else {
                Notification.show(TranslationUtils.getTranslation("login.failed-message.invalid-credentials"), 3000, Notification.Position.TOP_CENTER);
            }

        } catch (UserException e) {

            Notification.show(TranslationUtils.getTranslation("login.failed-message", e.getMessage()), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception e) {

            LOGGER.error("User login failed: email={}", email, e);

            Notification.show(TranslationUtils.getTranslation("login.failed-message.exception"), 3000, Notification.Position.TOP_CENTER);
        }
    }

    private void register() {

        if (!isFieldValid(registerEmail, TranslationUtils.getTranslation("register.email")) || !isEmailValid(registerEmail) ||
                !isFieldValid(registerPassword, TranslationUtils.getTranslation("register.password")) ||
                !isFieldValid(registerRetypePassword, TranslationUtils.getTranslation("register.retype-password"))) {
            return;
        }

        String email = registerEmail.getValue();
        String password = registerPassword.getValue();
        String retypePassword = registerRetypePassword.getValue();

        if (!password.equals(retypePassword)) {
            Notification.show(TranslationUtils.getTranslation("register.password-mismatch-message"), 1000, Notification.Position.TOP_CENTER);
            return;
        }

        try {

            UserDto userDto = new UserDto(email, password);

            userService.registerUser(userDto);
            tabs.setSelectedTab(loginTab);

            Notification.show(TranslationUtils.getTranslation("register.success-message"), 1000, Notification.Position.TOP_CENTER);

        } catch (UserException e) {

            LOGGER.error("User registration failed: Email={}", email, e);

            Notification.show(TranslationUtils.getTranslation("register.failed-message", e.getMessage()), 3000, Notification.Position.TOP_CENTER);

        } catch (Exception e) {

            LOGGER.error("User registration failed: Email={}", email, e);

            Notification.show(TranslationUtils.getTranslation("register.failed-message.exception"), 3000, Notification.Position.TOP_CENTER);
        }
    }

    private boolean isEmailValid(TextField field) {

        if (EMAIL_PATTERN.matcher(field.getValue()).matches()) {
            return true;
        }

        field.focus();
        field.setInvalid(true);
        field.getElement().callJsFunction("scrollIntoView");
        Notification.show(TranslationUtils.getTranslation("validation.invalid-email"), 3000, Notification.Position.TOP_CENTER);

        return false;
    }

    private boolean isFieldValid(TextFieldBase<?, String> field, String label) {

        if (field.isEmpty()) {

            field.focus();
            field.setInvalid(true);
            field.getElement().callJsFunction("scrollIntoView");
            Notification.show(TranslationUtils.getTranslation("validation.empty-field", label), 3000, Notification.Position.TOP_CENTER);

            return false;
        }

        field.setInvalid(false);
        return true;
    }
}

