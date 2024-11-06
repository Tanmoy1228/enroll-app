package com.example.application.views;

import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class AddressComponent extends AbstractFormView {

    private final ComboBox<String> country = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.country"));

    private final ComboBox<String> province = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.province"));

    private final ComboBox<String> region = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.region"));

    private final ComboBox<String> city = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.city"));

    private final TextField addressLine = createTextField(TranslationUtils.getTranslation("addressInfo.placeholder.addressLine"));

    private final Button saveButton = new Button();

    public AddressComponent(String title) {

        add(createAddressForm(title));

    }

    private Div createAddressForm(String title) {

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(country, TranslationUtils.getTranslation("addressInfo.label.country"), true),
                setupFormItem(province, TranslationUtils.getTranslation("addressInfo.label.province"), true),
                setupFormItem(region, TranslationUtils.getTranslation("addressInfo.label.region"), true),
                setupFormItem(city, TranslationUtils.getTranslation("addressInfo.label.city"), true),
                setupFormItem(addressLine, TranslationUtils.getTranslation("addressInfo.label.addressLine"), true),
                createSaveButton()
        );

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(title, layout);
    }

    @Override
    protected void configureSaveButton(Button saveButton) {
        saveButton.getStyle().set("width", "100px").set("margin-left", "auto");
    }

    @Override
    protected void configureNextPageButton(Button nextPageButton) {

    }
}
