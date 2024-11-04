package com.example.application.views;

import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@AnonymousAllowed
@PageTitle("Education Info")
@Route(value = "education-information", layout = MainLayout.class)
public class EducationInformationView extends AbstractFormView {

    private static final Logger LOGGER = LogManager.getLogger(EducationInformationView.class);

    private final ComboBox<String> level = createComboBox(TranslationUtils.getTranslation("educationInfo.label.level"));

    private final ComboBox<String> courseName = createComboBox(TranslationUtils.getTranslation("educationInfo.label.courseName"));

    private final ComboBox<String> country = createComboBox(TranslationUtils.getTranslation("educationInfo.label.country"));

    private final ComboBox<String> address = createComboBox(TranslationUtils.getTranslation("educationInfo.label.address"));

    private final ComboBox<String> school = createComboBox(TranslationUtils.getTranslation("educationInfo.label.school"));

    private final ComboBox<String> language = createComboBox(TranslationUtils.getTranslation("educationInfo.label.language"));

    private final ComboBox<String> attestatType = createComboBox(TranslationUtils.getTranslation("educationInfo.label.attestatType"));

    private final TextField attestatNo = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.attestatNo"));

    private final DatePicker givenDate = new DatePicker();

    private final ImageUploader applicationImageUploader = new ImageUploader(false);

    private final RadioButtonGroup<String> ortType = createRadioButtonGroup(TranslationUtils.getTranslation("educationInfo.ortType.didNotAttend"),
            TranslationUtils.getTranslation("educationInfo.ortType.attended"));

    private final TextField registrationNumber = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.registrationNumber"));

    private final TextField examScore = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.examScore"));

    private final DatePicker issuedDate = new DatePicker();

    private final ImageUploader ortImageUploader = new ImageUploader(true);


    public EducationInformationView() {

        createFormLayout();

        HorizontalLayout buttonLayout = createSaveAndNextButtonLayout();

        add(buttonLayout);
    }

    private void createFormLayout() {

        Div choicesSection = createChoicesSection();
        Div schoolInfoSection = createSchoolInfoSection();
        Div ortInfoSection = createOrtInfoSection();

        add(choicesSection, schoolInfoSection, ortInfoSection);
    }

    private Div createChoicesSection() {

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(level, TranslationUtils.getTranslation("educationInfo.choices.level"), true),
                setupFormItem(courseName, TranslationUtils.getTranslation("educationInfo.choices.courseName"), true)
        );

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(TranslationUtils.getTranslation("educationInfo.choices.title"), layout);
    }

    private Div createSchoolInfoSection() {

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(country, TranslationUtils.getTranslation("educationInfo.schoolInfo.country"), true),
                setupFormItem(address, TranslationUtils.getTranslation("educationInfo.schoolInfo.address"), true),
                setupFormItem(school, TranslationUtils.getTranslation("educationInfo.schoolInfo.school"), true),
                setupFormItem(language, TranslationUtils.getTranslation("educationInfo.schoolInfo.language"), true),
                setupFormItem(attestatType, TranslationUtils.getTranslation("educationInfo.schoolInfo.attestatType"), true),
                setupFormItem(attestatNo, TranslationUtils.getTranslation("educationInfo.schoolInfo.attestatNo"), true),
                setupFormItem(givenDate, TranslationUtils.getTranslation("educationInfo.schoolInfo.givenDate"), true),
                setupFormItem(applicationImageUploader, TranslationUtils.getTranslation("educationInfo.schoolInfo.imageUploader"), true)
        );

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(TranslationUtils.getTranslation("educationInfo.schoolInfo.title"), layout);
    }

    private Div createOrtInfoSection() {

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(ortType, TranslationUtils.getTranslation("educationInfo.ortInfo.type"), true),
                setupFormItem(registrationNumber, TranslationUtils.getTranslation("educationInfo.ortInfo.registrationNumber"), false),
                setupFormItem(examScore, TranslationUtils.getTranslation("educationInfo.ortInfo.examScore"), false),
                setupFormItem(issuedDate, TranslationUtils.getTranslation("educationInfo.ortInfo.issuedDate"), false),
                setupFormItem(ortImageUploader, "", false)
        );

        ortImageUploader.setVisible(false);

        ortType.addValueChangeListener(event -> {
            String selectedValue = event.getValue();
            ortImageUploader.setVisible(TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(selectedValue));
        });

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(TranslationUtils.getTranslation("educationInfo.ortInfo.title"), layout);
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
