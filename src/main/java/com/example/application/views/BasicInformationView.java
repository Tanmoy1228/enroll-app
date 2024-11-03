package com.example.application.views;

import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@AnonymousAllowed
@Route(value = "basic-information", layout = MainLayout.class)
@PageTitle("Basic Info")
public class BasicInformationView extends AbstractFormView {

    private static final Logger LOGGER = LogManager.getLogger(BasicInformationView.class);

    private final TextField firstName = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.firstName"));

    private final TextField lastName = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.lastName"));

    private final TextField nativeName = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.nativeName"));

    private final TextField nativeSurname = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.nativeSurname"));

    private final Checkbox patronymicCheckbox = new Checkbox(TranslationUtils.getTranslation("basicInfo.label.patronymic-check-box"));

    private final TextField patronymicTextField = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.patronymic"));

    private final DatePicker dateOfBirth = new DatePicker();

    private final ComboBox<String> citizenship = createComboBox(TranslationUtils.getTranslation("basicInfo.placeholder.citizenship"));

    private final ComboBox<String> nationality = createComboBox(TranslationUtils.getTranslation("basicInfo.placeholder.nationality"));

    private final RadioButtonGroup<String> gender = createRadioButtonGroup(
            TranslationUtils.getTranslation("basicInfo.gender.female"), TranslationUtils.getTranslation("basicInfo.gender.male"));

    private final RadioButtonGroup<String> maritalStatus = createRadioButtonGroup(
            TranslationUtils.getTranslation("basicInfo.maritalStatus.single"), TranslationUtils.getTranslation("basicInfo.maritalStatus.married"));

    private final TextField iin = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.iin"));
    private final TextField militaryRegistration = createTextField("");

    private final RadioButtonGroup<String> documentType = createRadioButtonGroup(
            TranslationUtils.getTranslation("basicInfo.documentType.identityCard"),
            TranslationUtils.getTranslation("basicInfo.documentType.passport"),
            TranslationUtils.getTranslation("basicInfo.documentType.certificateOfBirth"));

    private final TextField documentNo = createTextField(TranslationUtils.getTranslation("basicInfo.tooltip.documentNo"));

    private final TextField issuedBy = createTextField("");

    private final DatePicker issuanceDate = new DatePicker();

    private final ImageUploader imageUploader = new ImageUploader(false);


    public BasicInformationView() {

        createFormLayout();

        HorizontalLayout buttonLayout = createSaveAndNextButtonLayout();

        add(buttonLayout);
    }

    private void createFormLayout() {

        Div basicInfoSection = createBasicInfoSection();
        Div documentSection = createDocumentSection();

        add(basicInfoSection, documentSection);
    }

    private Div createBasicInfoSection() {

        citizenship.setItems(List.of("Bangladesh", "Russia", "Kyrgyzstan"));
        nationality.setItems(List.of("Bangladeshi", "Russian", "Kyrgyz"));

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(firstName, TranslationUtils.getTranslation("basicInfo.label.firstName"), true),
                setupFormItem(lastName, TranslationUtils.getTranslation("basicInfo.label.lastName"), true),
                setupFormItem(nativeName, TranslationUtils.getTranslation("basicInfo.label.nativeName"), true),
                setupFormItem(nativeSurname, TranslationUtils.getTranslation("basicInfo.label.nativeSurname"), true),
                createPatronymicTextFieldAndCheckBoxCombo(patronymicCheckbox, patronymicTextField),
                setupFormItem(dateOfBirth, TranslationUtils.getTranslation("basicInfo.label.dateOfBirth"), true),
                setupFormItem(citizenship, TranslationUtils.getTranslation("basicInfo.label.citizenship"), true),
                setupFormItem(nationality, TranslationUtils.getTranslation("basicInfo.label.nationality"), true),
                setupFormItem(gender, TranslationUtils.getTranslation("basicInfo.label.gender"), true),
                setupFormItem(maritalStatus, TranslationUtils.getTranslation("basicInfo.label.maritalStatus"), true),
                setupFormItem(iin, TranslationUtils.getTranslation("basicInfo.label.iin"), true),
                setupFormItem(militaryRegistration, TranslationUtils.getTranslation("basicInfo.label.militaryRegistration"), false)
        );

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(TranslationUtils.getTranslation("basicInfo.title"), layout);
    }

    private Div createDocumentSection() {

        documentType.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(documentType, TranslationUtils.getTranslation("basicInfo.label.documentType"), true),
                setupFormItem(documentNo, TranslationUtils.getTranslation("basicInfo.label.documentNo"), true),
                setupFormItem(issuedBy, TranslationUtils.getTranslation("basicInfo.label.issuedBy"), true),
                setupFormItem(issuanceDate, TranslationUtils.getTranslation("basicInfo.label.issuanceDate"), true),
                setupFormItem(imageUploader, TranslationUtils.getTranslation("basicInfo.label.imageUploader"), true)
        );

        return createSectionContainer(TranslationUtils.getTranslation("basicInfo.document.title"), layout);
    }

    private HorizontalLayout createPatronymicTextFieldAndCheckBoxCombo(Checkbox checkbox, TextField textField) {

        textField.setWidth("300px");

        checkbox.addValueChangeListener(event -> {
            boolean isChecked = event.getValue();
            if (isChecked) {
                textField.setEnabled(false);
                textField.getStyle().set("background-color", "#a0a0a0");
            } else {
                textField.setEnabled(true);
                textField.getStyle().remove("background-color");
            }
        });

        Span label = createLabel(TranslationUtils.getTranslation("basicInfo.label.patronymic-text-field"), true);

        VerticalLayout checkBoxAndTextFieldGroup = new VerticalLayout(checkbox, textField);

        checkBoxAndTextFieldGroup.setWidth("300px");
        checkBoxAndTextFieldGroup.setSpacing(false);
        checkBoxAndTextFieldGroup.setPadding(false);
        checkBoxAndTextFieldGroup.setAlignItems(Alignment.START);

        HorizontalLayout layout = new HorizontalLayout(label, checkBoxAndTextFieldGroup);
        layout.setAlignItems(Alignment.CENTER);

        return layout;
    }

    @Override
    protected void configureSaveButton(Button saveButton) {
        saveButton.addClickListener(event -> {
            LOGGER.info("Save button clicked.");
        });
    }

    @Override
    protected void configureNextPageButton(Button nextPageButton) {
        nextPageButton.addClickListener(event -> {
            LOGGER.info("Next page button clicked.");
        });
    }
}

