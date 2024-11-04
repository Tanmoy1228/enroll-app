package com.example.application.views;

import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public abstract class AbstractFormView extends SecuredView {

    protected static TextField createTextField(String tooltip) {

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);

        if (tooltip != null && !tooltip.isEmpty()) {
            textField.getElement().setProperty("title", tooltip);
        }

        return textField;
    }

    protected static <T> ComboBox<T> createComboBox(String placeholder) {

        ComboBox<T> comboBox = new ComboBox<>();

        comboBox.setPlaceholder(placeholder);
        comboBox.getElement().setProperty("title", placeholder);

        return comboBox;
    }

    protected static RadioButtonGroup<String> createRadioButtonGroup(String... items) {

        RadioButtonGroup<String> group = new RadioButtonGroup<>();

        group.setItems(items);

        return group;
    }

    protected Span createLabel(String labelText, boolean isRequired) {

        Span label = new Span(labelText);

        label.getStyle()
                .set("width", "300px")
                .set("font-size", "18px")
                .set("font-weight", "bold")
                .set("margin-left", "50px")
                .set("margin-right", "50px");

        if (isRequired) {

            Span requiredIndicator = new Span("*");
            requiredIndicator.getStyle()
                    .set("color", "red")
                    .set("font-size", "12px")
                    .set("font-weight", "bold")
                    .set("margin-left", "5px");

            label.add(requiredIndicator);
        }

        return label;
    }

    protected HorizontalLayout setupFormItem(Component component, String labelText, boolean isRequired) {

        component.getElement().getStyle().set("width", "300px");
        component.getElement().setProperty("required", isRequired);

        Span label = createLabel(labelText, isRequired);

        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setAlignItems(Alignment.CENTER);

        return layout;
    }

    protected Div createSectionContainer(String title, VerticalLayout content) {

        H2 sectionTitle = new H2(title);
        sectionTitle.getStyle().set("font-weight", "bold").set("color", "orange");

        VerticalLayout containerLayout = new VerticalLayout(sectionTitle, content);
        containerLayout.getStyle().set("border", "1px solid green").set("padding", "15px");

        return new Div(containerLayout);
    }

    protected Button createButton(String text, String backgroundColor, String color) {

        Button button = new Button(text);

        button.getStyle()
                .set("width", "300px")
                .set("height", "50px")
                .set("background-color", backgroundColor)
                .set("color", color);

        return button;
    }

    protected Button createSaveButton() {

        Button saveButton = createButton(TranslationUtils.getTranslation("button.label.save"), "green", "white");

        configureSaveButton(saveButton);

        return saveButton;
    }

    protected Button createNextPageButton() {

        Button nextPageButton = createButton(TranslationUtils.getTranslation("button.label.next-page"), "#007bff", "white");

        configureNextPageButton(nextPageButton);

        return nextPageButton;
    }

    protected HorizontalLayout createSaveAndNextButtonLayout() {

        Button saveButton = createSaveButton();
        Button nextPageButton = createNextPageButton();

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, nextPageButton);

        buttonLayout.setSpacing(true);
        buttonLayout.setPadding(true);
        buttonLayout.getStyle().set("margin-left", "50px");
        buttonLayout.setAlignItems(Alignment.CENTER);

        return buttonLayout;
    }

    protected abstract void configureSaveButton(Button saveButton);

    protected abstract void configureNextPageButton(Button nextPageButton);
}
