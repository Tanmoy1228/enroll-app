package com.example.application.views;

import com.example.application.dto.DocumentType;
import com.example.application.dto.Gender;
import com.example.application.dto.MaritalStatus;
import com.example.application.entity.BasicInfo;
import com.example.application.entity.Country;
import com.example.application.entity.Nationality;
import com.example.application.services.BasicInfoService;
import com.example.application.services.CountryService;
import com.example.application.services.NationalityService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;

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

    private final ComboBox<Country> citizenship = createComboBox(TranslationUtils.getTranslation("basicInfo.placeholder.citizenship"));

    private final ComboBox<Nationality> nationality = createComboBox(TranslationUtils.getTranslation("basicInfo.placeholder.nationality"));

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

    private BasicInfo basicInfo;
    private final CountryService countryService;
    private final BasicInfoService basicInfoService;
    private final NationalityService nationalityService;
    private final Binder<BasicInfo> binder = new Binder<>(BasicInfo.class);

    @Autowired
    public BasicInformationView(CountryService countryService,
                                BasicInfoService basicInfoService,
                                NationalityService nationalityService) {

        this.countryService = countryService;
        this.basicInfoService = basicInfoService;
        this.nationalityService = nationalityService;

        createFormLayout();

        HorizontalLayout buttonLayout = createSaveAndNextButtonLayout();

        add(buttonLayout);

        addAttachListener(event -> initializeData());
    }

    private void createFormLayout() {

        Div basicInfoSection = createBasicInfoSection();
        Div documentSection = createDocumentSection();

        add(basicInfoSection, documentSection);
    }

    private Div createBasicInfoSection() {

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
                textField.setInvalid(false);
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
            saveData();
        });
    }

    @Override
    protected void configureNextPageButton(Button nextPageButton) {
        nextPageButton.addClickListener(event -> {
            try {
                validateImage();
                binder.writeBean(basicInfo);

                String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");
                BasicInfo savedBasicInfo = basicInfoService.findBasicInfoByEmail(userEmail);

                if (savedBasicInfo == null) {
                    Notification.show(TranslationUtils.getTranslation("notification.dataNotSaved"), 3000, Notification.Position.TOP_CENTER);
                    LOGGER.warn("Data not found. User must save their information before proceeding.");
                }

                VaadinSession.getCurrent().setAttribute("selectedTabIndex", 1);
                UI.getCurrent().navigate(EducationInformationView.class);

            } catch (Exception e) {
                Notification.show(TranslationUtils.getTranslation("notification.dataNotSaved"), 3000, Notification.Position.TOP_CENTER);
                LOGGER.warn("Data not found.", e);
                throw new RuntimeException(e);
            }
        });
    }

    private void initializeData() {

        initializeCitizenship();
        initializeNationality();
        setupBinderAndLoadData();
    }

    private void initializeCitizenship() {
        try {
            List<Country> countries = countryService.findAll();
            LOGGER.info("Load countries data. Size = {}", countries.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                citizenship.setItemLabelGenerator(Country::getName);
                citizenship.setItems(countries);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load citizenship data", e);
        }
    }

    private void initializeNationality() {

        try {
            List<Nationality> nationalities = nationalityService.findAll();
            LOGGER.info("Load nationality data. Size = {}", nationalities.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                nationality.setItemLabelGenerator(Nationality::getName);
                nationality.setItems(nationalities);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load nationality data", e);
        }
    }

    private void setupBinderAndLoadData() {

        CompletableFuture.runAsync(() -> {
            try {
                getUI().ifPresent(ui -> ui.access(() -> {
                    setupBinder();
                    loadBasicInfo();
                }));
            } catch (Exception e) {
                LOGGER.error("Failed to load user data", e);
            }
        });
    }

    private void loadBasicInfo() {

        String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");

        try {
            basicInfo = basicInfoService.findBasicInfoByEmail(userEmail);
            binder.readBean(basicInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to load basicInfo", e);
        }

        if (basicInfo != null && basicInfo.getPatronymic().isEmpty()) {
            patronymicCheckbox.setValue(true);
        }

        if (basicInfo == null) {
            basicInfo = new BasicInfo();
        }

        loadImages();
    }

    private void loadImages() {

        if (basicInfo.getDocumentImage1() != null) {
            imageUploader.loadImage1(basicInfo.getDocumentImage1());
        }
        if (basicInfo.getDocumentImage2() != null) {
            imageUploader.loadImage2(basicInfo.getDocumentImage2());
        }
    }

    private void validateImage() throws Exception {

        if (!imageUploader.isImage1Present() && !imageUploader.isImage2Present()) {
            throw new Exception(TranslationUtils.getTranslation("exception.uploadValidAttachment"));
        }
        if (imageUploader.isImage1Present()) {
            basicInfo.setDocumentImage1(imageUploader.getUploadedImage1AsBytes());
        }
        if (imageUploader.isImage2Present()) {
            basicInfo.setDocumentImage2(imageUploader.getUploadedImage2AsBytes());
        }
    }

    private void saveData() {

        try {

            validateImage();

            binder.writeBean(basicInfo);

            basicInfo.setEmail((String) VaadinSession.getCurrent().getAttribute("email"));

            basicInfoService.save(basicInfo);
            Notification.show(TranslationUtils.getTranslation("notification.dataSavedSuccessfully"), 3000, Notification.Position.TOP_CENTER);

        } catch (ValidationException e) {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 3000, Notification.Position.TOP_CENTER);
        } catch (Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setupBinder() {

        bindFirstNameField();
        bindLastNameField();
        bindNativeNameField();
        bindNativeSurnameField();
        bindPatronymicField();
        bindDateOfBirthField();
        bindCitizenshipField();
        bindNationalityField();
        bindGenderField();
        bindMaritalStatusField();
        bindIINField();
        bindMilitaryRegistrationField();
        bindDocumentTypeField();
        bindDocumentNoField();
        bindIssuedByField();
        bindIssuanceDateField();
    }

    private void bindFirstNameField() {
        binder.forField(firstName)
                .asRequired(TranslationUtils.getTranslation("validation.required.firstName"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.firstName"), 1, 255))
                .withValidator(name -> name.matches("^[a-zA-Zа-яА-ЯёЁ\\u0400-\\u04FF\\s]+$"),
                        TranslationUtils.getTranslation("validation.invalid.firstName"))
                .bind(BasicInfo::getFirstName, BasicInfo::setFirstName);
    }

    private void bindLastNameField() {
        binder.forField(lastName)
                .asRequired(TranslationUtils.getTranslation("validation.required.lastName"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.lastName"), 1, 255))
                .withValidator(name -> name.matches("^[a-zA-Zа-яА-ЯёЁ\\u0400-\\u04FF\\s]+$"),
                        TranslationUtils.getTranslation("validation.invalid.lastName"))
                .bind(BasicInfo::getLastName, BasicInfo::setLastName);
    }

    private void bindNativeNameField() {
        binder.forField(nativeName)
                .asRequired(TranslationUtils.getTranslation("validation.required.nativeName"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.nativeName"), 1, 255))
                .withValidator(name -> name.matches("^[a-zA-Zа-яА-ЯёЁ\\u0400-\\u04FF\\s]+$"),
                        TranslationUtils.getTranslation("validation.invalid.nativeName"))
                .bind(BasicInfo::getNativeName, BasicInfo::setNativeName);
    }

    private void bindNativeSurnameField() {
        binder.forField(nativeSurname)
                .asRequired(TranslationUtils.getTranslation("validation.required.nativeSurname"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.nativeSurname"), 1, 255))
                .withValidator(name -> name.matches("^[a-zA-Zа-яА-ЯёЁ\\u0400-\\u04FF\\s]+$"),
                        TranslationUtils.getTranslation("validation.invalid.nativeSurname"))
                .bind(BasicInfo::getNativeSurname, BasicInfo::setNativeSurname);
    }

    private void bindPatronymicField() {
        binder.forField(patronymicTextField)
                .withValidator(value -> {
                    if (patronymicCheckbox.getValue()) {
                        return value == null || value.trim().isEmpty();
                    } else {
                        return value != null && !value.trim().isEmpty();
                    }
                }, TranslationUtils.getTranslation("validation.required.patronymic"))
                .bind(BasicInfo::getPatronymic, BasicInfo::setPatronymic);
    }

    private void bindDateOfBirthField() {
        binder.forField(dateOfBirth)
                .asRequired(TranslationUtils.getTranslation("validation.required.dateOfBirth"))
                .bind(BasicInfo::getDateOfBirth, BasicInfo::setDateOfBirth);
    }

    private void bindCitizenshipField() {
        binder.forField(citizenship)
                .asRequired(TranslationUtils.getTranslation("validation.required.citizenship"))
                .withConverter(
                        country -> country != null ? country.getId() : null,
                        id -> id != null ? countryService.findById(id) : null
                )
                .bind(BasicInfo::getCitizenshipId, BasicInfo::setCitizenshipId);
    }

    private void bindNationalityField() {
        binder.forField(nationality)
                .asRequired(TranslationUtils.getTranslation("validation.required.nationality"))
                .withConverter(
                        nationality -> nationality != null ? nationality.getId() : null,
                        id -> id != null ? nationalityService.findById(id) : null
                )
                .bind(BasicInfo::getNationalityId, BasicInfo::setNationalityId);
    }

    private void bindGenderField() {
        binder.forField(gender)
                .asRequired(TranslationUtils.getTranslation("validation.required.gender"))
                .withConverter(
                        genderString -> {
                            if (genderString.equals(TranslationUtils.getTranslation("basicInfo.gender.female"))) {
                                return Gender.FEMALE;
                            } else if (genderString.equals(TranslationUtils.getTranslation("basicInfo.gender.male"))) {
                                return Gender.MALE;
                            }
                            return Gender.MALE;
                        },
                        genderEnum -> {
                            if (genderEnum == Gender.FEMALE) {
                                return TranslationUtils.getTranslation("basicInfo.gender.female");
                            } else if (genderEnum == Gender.MALE) {
                                return TranslationUtils.getTranslation("basicInfo.gender.male");
                            }
                            return "";
                        }
                )
                .withValidationStatusHandler(status -> {
                    if (status.isError() && gender.isInvalid()) {
                        gender.setInvalid(true);
                        gender.setErrorMessage(status.getMessage().orElse(""));
                    } else {
                        gender.setInvalid(false);
                    }
                })
                .bind(BasicInfo::getGender, BasicInfo::setGender);
    }

    private void bindMaritalStatusField() {

        binder.forField(maritalStatus)
                .asRequired(TranslationUtils.getTranslation("validation.required.maritalStatus"))
                .withConverter(
                        statusString -> {
                            if (statusString.equals(TranslationUtils.getTranslation("basicInfo.maritalStatus.single"))) {
                                return MaritalStatus.SINGLE;
                            } else if (statusString.equals(TranslationUtils.getTranslation("basicInfo.maritalStatus.married"))) {
                                return MaritalStatus.MARRIED;
                            }
                            return null;
                        },
                        statusEnum -> {
                            if (statusEnum == MaritalStatus.SINGLE) {
                                return TranslationUtils.getTranslation("basicInfo.maritalStatus.single");
                            } else if (statusEnum == MaritalStatus.MARRIED) {
                                return TranslationUtils.getTranslation("basicInfo.maritalStatus.married");
                            }
                            return "";
                        }
                )
                .withValidationStatusHandler(status -> {
                    if (status.isError() && maritalStatus.isInvalid()) {
                        maritalStatus.setInvalid(true);
                        maritalStatus.setVisible(status.isError());
                        maritalStatus.setErrorMessage(status.getMessage().orElse(""));
                    } else {
                        maritalStatus.setInvalid(false);
                    }
                })
                .bind(BasicInfo::getMaritalStatus, BasicInfo::setMaritalStatus);
    }

    private void bindIINField() {
        binder.forField(iin)
                .asRequired(TranslationUtils.getTranslation("validation.required.iin"))
                .withValidator(value -> value.matches("\\d+"), TranslationUtils.getTranslation("validation.invalid.iin"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.iin"), 10, 50))
                .bind(BasicInfo::getIin, BasicInfo::setIin);
    }

    private void bindMilitaryRegistrationField() {

        binder.forField(militaryRegistration)
                .bind(BasicInfo::getMilitaryRegistration, BasicInfo::setMilitaryRegistration);
    }

    private void bindDocumentTypeField() {

        binder.forField(documentType)
                .asRequired(TranslationUtils.getTranslation("validation.required.documentType"))
                .withConverter(
                        documentTypeString -> {
                            if (documentTypeString.equals(TranslationUtils.getTranslation("basicInfo.documentType.identityCard"))) {
                                return DocumentType.IDENTITY_CARD;
                            } else if (documentTypeString.equals(TranslationUtils.getTranslation("basicInfo.documentType.passport"))) {
                                return DocumentType.PASSPORT;
                            } else if (documentTypeString.equals(TranslationUtils.getTranslation("basicInfo.documentType.certificateOfBirth"))) {
                                return DocumentType.BIRTH_CERTIFICATE;
                            }
                            return null;
                        },
                        documentTypeEnum -> {
                            if (documentTypeEnum == DocumentType.IDENTITY_CARD) {
                                return TranslationUtils.getTranslation("basicInfo.documentType.identityCard");
                            } else if (documentTypeEnum == DocumentType.PASSPORT) {
                                return TranslationUtils.getTranslation("basicInfo.documentType.passport");
                            } else if (documentTypeEnum == DocumentType.BIRTH_CERTIFICATE) {
                                return TranslationUtils.getTranslation("basicInfo.documentType.certificateOfBirth");
                            }
                            return "";
                        }
                )
                .bind(BasicInfo::getDocumentType, BasicInfo::setDocumentType);
    }

    private void bindDocumentNoField() {
        binder.forField(documentNo)
                .asRequired(TranslationUtils.getTranslation("validation.required.documentNo"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.documentNo"), 1, 100))
                .withValidator(value -> value.matches("^[A-Z0-9]+$"),
                        TranslationUtils.getTranslation("validation.invalid.documentNo"))
                .bind(BasicInfo::getDocumentNo, BasicInfo::setDocumentNo);
    }

    private void bindIssuedByField() {
        binder.forField(issuedBy)
                .asRequired(TranslationUtils.getTranslation("validation.required.issuedBy"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.issuedBy"), 1, 255))
                .withValidator(name -> name.matches("^[a-zA-Zа-яА-ЯёЁ\\u0400-\\u04FF\\s]+$"),
                        TranslationUtils.getTranslation("validation.invalid.issuedBy"))
                .bind(BasicInfo::getIssuedBy, BasicInfo::setIssuedBy);
    }

    private void bindIssuanceDateField() {
        binder.forField(issuanceDate)
                .asRequired(TranslationUtils.getTranslation("validation.required.issuanceDate"))
                .bind(BasicInfo::getIssuanceDate, BasicInfo::setIssuanceDate);
    }

}

