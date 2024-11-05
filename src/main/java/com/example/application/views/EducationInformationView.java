package com.example.application.views;

import com.example.application.entity.*;
import com.example.application.services.*;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.converter.Converter;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@AnonymousAllowed
@PageTitle("Education Info")
@Route(value = "education-information", layout = MainLayout.class)
public class EducationInformationView extends AbstractFormView {

    private static final Logger LOGGER = LogManager.getLogger(EducationInformationView.class);

    private final ComboBox<Level> level = createComboBox(TranslationUtils.getTranslation("educationInfo.label.level"));

    private final ComboBox<Course> courseName = createComboBox(TranslationUtils.getTranslation("educationInfo.label.courseName"));

    private final ComboBox<Country> country = createComboBox(TranslationUtils.getTranslation("educationInfo.label.country"));

    private final ComboBox<Address> address = createComboBox(TranslationUtils.getTranslation("educationInfo.label.address"));

    private final ComboBox<School> school = createComboBox(TranslationUtils.getTranslation("educationInfo.label.school"));

    private final ComboBox<Language> language = createComboBox(TranslationUtils.getTranslation("educationInfo.label.language"));

    private final ComboBox<AttestatType> attestatType = createComboBox(TranslationUtils.getTranslation("educationInfo.label.attestatType"));

    private final TextField attestatNo = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.attestatNo"));

    private final DatePicker givenDate = new DatePicker();

    private final ImageUploader applicationImageUploader = new ImageUploader(false);

    private final RadioButtonGroup<String> ortType = createRadioButtonGroup(TranslationUtils.getTranslation("educationInfo.ortType.didNotAttend"),
            TranslationUtils.getTranslation("educationInfo.ortType.attended"));

    private final TextField registrationNumber = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.registrationNumber"));

    private final TextField examScore = createTextField(TranslationUtils.getTranslation("educationInfo.tooltip.examScore"));

    private final DatePicker issuedDate = new DatePicker();

    private final ImageUploader ortImageUploader = new ImageUploader(true);

    private boolean isSavedOperation = false;

    private EducationInfo educationInfo;

    private final LevelService levelService;

    private final SchoolService schoolService;

    private final CourseService courseService;

    private final AddressService addressService;

    private final CountryService countryService;

    private final LanguageService languageService;

    private final AttestatTypeService attestatTypeService;

    private final EducationInfoService educationInfoService;

    private final Binder<EducationInfo> binder = new Binder<>(EducationInfo.class);

    @Autowired
    public EducationInformationView(LevelService levelService,
                                    SchoolService schoolService,
                                    CourseService courseService,
                                    AddressService addressService,
                                    CountryService countryService,
                                    LanguageService languageService,
                                    AttestatTypeService attestatTypeService,
                                    EducationInfoService educationInfoService) {

        this.levelService = levelService;
        this.schoolService = schoolService;
        this.courseService = courseService;
        this.addressService = addressService;
        this.countryService = countryService;
        this.languageService = languageService;
        this.attestatTypeService = attestatTypeService;
        this.educationInfoService = educationInfoService;

        createFormLayout();

        HorizontalLayout buttonLayout = createSaveAndNextButtonLayout();

        add(buttonLayout);

        addAttachListener(event -> initializeData());
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
            configureOrtTypeListener(selectedValue);
        });

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(TranslationUtils.getTranslation("educationInfo.ortInfo.title"), layout);
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
                binder.writeBean(educationInfo);

                String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");
                EducationInfo savedEducationInfo = educationInfoService.findEducationInfoByEmail(userEmail);

                if (savedEducationInfo == null) {
                    Notification.show(TranslationUtils.getTranslation("notification.dataNotSaved"), 3000, Notification.Position.TOP_CENTER);
                    LOGGER.warn("Data not found. User must save their information before proceeding.");
                }

                VaadinSession.getCurrent().setAttribute("selectedTabIndex", 2);
                UI.getCurrent().navigate(ContactInformationView.class);

            } catch (Exception e) {
                Notification.show(TranslationUtils.getTranslation("notification.dataNotSaved"), 3000, Notification.Position.TOP_CENTER);
                LOGGER.warn("Data not found.", e);
                throw new RuntimeException(e);
            }
        });
    }

    private void initializeData() {

        initializeLevels();
        initializeCountry();
        initializeLanguage();
        initializeAttestatType();

        configureLevelChangeListener();
        configureCountryChangeListener();
        configureAddressChangeListener();

        setupBinderAndLoadData();
    }

    private void configureLevelChangeListener() {

        level.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            courseName.clear();

            Level selectedLevel = event.getValue();

            if (selectedLevel != null) {

                Map<Faculty, List<Course>> courseListMap = courseService.getCoursesByLevel(selectedLevel.getId());

                List<Course> courseList = new ArrayList<>();

                courseListMap.forEach((faculty, courses) -> {
                    courses.forEach(course -> {
                        course.setName(course.getCode() + ", " + course.getName() + "- (" + faculty.getName() + ")");
                        courseList.add(course);
                    });
                });

                courseName.setItemLabelGenerator(Course::getName);
                courseName.setItems(courseList);
            }
        });
    }

    private void configureCountryChangeListener() {

        country.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            school.clear();
            address.clear();

            Country selectedCountry = event.getValue();

            if (selectedCountry != null) {
                address.setItemLabelGenerator(Address::getName);
                address.setItems(addressService.getAddressesByCountryId(selectedCountry.getId()));
            }
        });
    }

    private void configureAddressChangeListener() {

        address.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            school.clear();

            Address selectedAddress = event.getValue();

            if (selectedAddress != null) {
                school.setItemLabelGenerator(School::getName);
                school.setItems(schoolService.getSchoolByAddress(selectedAddress.getId()));
            }
        });
    }

    private void configureOrtTypeListener(String ortTypeValue) {

        ortImageUploader.setVisible(TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(ortTypeValue));

        if (TranslationUtils.getTranslation("educationInfo.ortType.didNotAttend").equals(ortTypeValue)) {

            registrationNumber.clear();
            registrationNumber.setInvalid(false);
            registrationNumber.setEnabled(false);
            registrationNumber.getStyle().set("background-color", "#a0a0a0");

            examScore.clear();
            examScore.setInvalid(false);
            examScore.setEnabled(false);
            examScore.getStyle().set("background-color", "#a0a0a0");

            issuedDate.clear();
            issuedDate.setInvalid(false);
            issuedDate.setEnabled(false);
            issuedDate.getStyle().set("background-color", "#a0a0a0");

        } else {
            registrationNumber.setEnabled(true);
            registrationNumber.getStyle().remove("background-color");

            examScore.setEnabled(true);
            examScore.getStyle().remove("background-color");

            issuedDate.setEnabled(true);
            issuedDate.getStyle().remove("background-color");
        }
    }

    private void initializeLevels() {
        try {
            List<Level> levels = levelService.findAll();
            LOGGER.info("Load levels data. Size = {}", levels.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                level.setItemLabelGenerator(Level::getName);
                level.setItems(levels);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load levels data", e);
        }
    }

    private void initializeCountry() {
        try {
            List<Country> countries = countryService.findAll();
            LOGGER.info("Load countries data. Size = {}", countries.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                country.setItemLabelGenerator(Country::getName);
                country.setItems(countries);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load countries data", e);
        }
    }

    private void initializeLanguage() {
        try {
            List<Language> languages = languageService.findAll();
            LOGGER.info("Load languages data. Size = {}", languages.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                language.setItemLabelGenerator(Language::getName);
                language.setItems(languages);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load languages data", e);
        }
    }

    private void initializeAttestatType() {

        try {
            List<AttestatType> attestatTypes = attestatTypeService.findAll();
            LOGGER.info("Load attestat type data. Size = {}", attestatTypes.size());
            getUI().ifPresent(ui -> ui.access(() -> {
                attestatType.setItemLabelGenerator(AttestatType::getName);
                attestatType.setItems(attestatTypes);
            }));
        } catch (Exception e) {
            LOGGER.error("Failed to load attestat type data", e);
        }
    }

    private void setupBinderAndLoadData() {

        CompletableFuture.runAsync(() -> {
            try {
                getUI().ifPresent(ui -> ui.access(() -> {
                    setupBinder();
                    loadEducationalInfo();
                }));
            } catch (Exception e) {
                LOGGER.error("Failed to load user data", e);
            }
        });
    }

    private void loadEducationalInfo() {

        String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");

        try {
            educationInfo = educationInfoService.findEducationInfoByEmail(userEmail);
            binder.readBean(educationInfo);
        } catch (Exception e) {
            LOGGER.error("Failed to load educationInfo", e);
        }

        if (educationInfo == null) {
            educationInfo = new EducationInfo();
        }

        loadImages();
    }

    private void loadImages() {

        if (educationInfo.getDocumentImage1() != null) {
            applicationImageUploader.loadImage1(educationInfo.getDocumentImage1());
        }

        if (educationInfo.getDocumentImage2() != null) {
            applicationImageUploader.loadImage2(educationInfo.getDocumentImage2());
        }

        if (educationInfo.getOrtCertificateImage() != null) {
            ortImageUploader.loadImage1(educationInfo.getOrtCertificateImage());
        }
    }

    private void validateImage() throws Exception {

        if (!applicationImageUploader.isImage1Present() && !applicationImageUploader.isImage2Present()) {
            throw new Exception(TranslationUtils.getTranslation("exception.uploadValidAttachment"));
        }

        if (applicationImageUploader.isImage1Present()) {
            educationInfo.setDocumentImage1(applicationImageUploader.getUploadedImage1AsBytes());
        }
        if (applicationImageUploader.isImage2Present()) {
            educationInfo.setDocumentImage2(applicationImageUploader.getUploadedImage2AsBytes());
        }

        if (ortImageUploader.isImage1Present()) {
            educationInfo.setOrtCertificateImage(ortImageUploader.getUploadedImage1AsBytes());
        }
    }

    private void saveData() {

        try {

            isSavedOperation = true;

            validateImage();

            binder.writeBean(educationInfo);

            educationInfo.setEmail((String) VaadinSession.getCurrent().getAttribute("email"));

            educationInfoService.save(educationInfo);
            Notification.show(TranslationUtils.getTranslation("notification.dataSavedSuccessfully"), 3000, Notification.Position.TOP_CENTER);

            isSavedOperation = false;

        } catch (ValidationException e) {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 3000, Notification.Position.TOP_CENTER);
        } catch (Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void setupBinder() {

        bindLevelField();
        bindCourseNameField();
        bindCountryField();
        bindAddressField();
        bindSchoolField();
        bindLanguageField();
        bindAttestatTypeField();
        bindAttestatNoField();
        bindGivenDateField();
        bindOrtTypeField();
        bindRegistrationNumberField();
        bindExamScoreField();
        bindIssuedDateField();
    }

    private void bindLevelField() {

        binder.forField(level)
                .asRequired(TranslationUtils.getTranslation("validation.required.level"))
                .withConverter(
                        level -> level != null ? level.getId() : null,
                        id -> id != null ? levelService.findById(id) : null
                )
                .bind(EducationInfo::getLevelId, EducationInfo::setLevelId);
    }

    private void bindCourseNameField() {

        binder.forField(courseName)
                .asRequired(TranslationUtils.getTranslation("validation.required.courserName"))
                .withConverter(
                        course -> course != null ? course.getId() : null,
                        id -> id != null ? courseService.findById(id) : null
                )
                .bind(EducationInfo::getCourseId, EducationInfo::setCourseId);
    }

    private void bindCountryField() {

        binder.forField(country)
                .asRequired(TranslationUtils.getTranslation("validation.required.country"))
                .withConverter(
                        country -> country != null ? country.getId() : null,
                        id -> id != null ? countryService.findById(id) : null
                )
                .bind(EducationInfo::getCountryId, EducationInfo::setCountryId);
    }

    private void bindAddressField() {

        binder.forField(address)
                .asRequired(TranslationUtils.getTranslation("validation.required.address"))
                .withConverter(
                        address -> address != null ? address.getId() : null,
                        id -> id != null ? addressService.findById(id) : null
                )
                .bind(EducationInfo::getAddressId, EducationInfo::setAddressId);
    }

    private void bindSchoolField() {

        binder.forField(school)
                .asRequired(TranslationUtils.getTranslation("validation.required.school"))
                .withConverter(
                        school -> school != null ? school.getId() : null,
                        id -> id != null ? schoolService.findById(id) : null
                )
                .bind(EducationInfo::getSchoolId, EducationInfo::setSchoolId);
    }

    private void bindLanguageField() {

        binder.forField(language)
                .asRequired(TranslationUtils.getTranslation("validation.required.language"))
                .withConverter(
                        language -> language != null ? language.getId() : null,
                        id -> id != null ? languageService.findById(id) : null
                )
                .bind(EducationInfo::getLanguageId, EducationInfo::setLanguageId);
    }

    private void bindAttestatTypeField() {

        binder.forField(attestatType)
                .asRequired(TranslationUtils.getTranslation("validation.required.attestatType"))
                .withConverter(
                        attestatType -> attestatType != null ? attestatType.getId() : null,
                        id -> id != null ? attestatTypeService.findById(id) : null
                )
                .bind(EducationInfo::getAttestatTypeId, EducationInfo::setAttestatTypeId);
    }

    private void bindAttestatNoField() {

        binder.forField(attestatNo)
                .asRequired(TranslationUtils.getTranslation("validation.required.attestatNo"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.attestatNo"), 1, 100))
                .withValidator(value -> value.matches("^[a-zA-Z0-9]+$"),
                        TranslationUtils.getTranslation("validation.invalid.attestatNo"))
                .bind(EducationInfo::getAttestatNo, EducationInfo::setAttestatNo);
    }

    private void bindGivenDateField() {
        binder.forField(givenDate)
                .asRequired(TranslationUtils.getTranslation("validation.required.givenDate"))
                .bind(EducationInfo::getGivenDate, EducationInfo::setGivenDate);
    }

    private void bindOrtTypeField() {

        binder.forField(ortType)
                .asRequired(TranslationUtils.getTranslation("validation.required.ortType"))
                .withConverter(new Converter<String, Boolean>() {
                    @Override
                    public Result<Boolean> convertToModel(String value, ValueContext context) {
                        boolean attend = TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(value);
                        return Result.ok(attend);
                    }

                    @Override
                    public String convertToPresentation(Boolean value, ValueContext context) {
                        return value ? TranslationUtils.getTranslation("educationInfo.ortType.attended")
                                : TranslationUtils.getTranslation("educationInfo.ortType.didNotAttend");
                    }
                })
                .bind(
                        EducationInfo::isAttendOrt,
                        EducationInfo::setAttendOrt
                );
    }

    private void bindRegistrationNumberField() {

        binder.forField(registrationNumber)
                .withValidator(value -> {
                    if (TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(ortType.getValue())) {
                        return value != null && !value.trim().isEmpty();
                    }
                    return true;
                }, TranslationUtils.getTranslation("validation.required.registrationNumber"))
                .withValidator(value -> {
                    if (TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(ortType.getValue()) &&
                            value != null && !value.trim().isEmpty()) {
                        return value.length() == 7 && value.matches("^[a-zA-Z0-9]+$");
                    }
                    return true;
                }, TranslationUtils.getTranslation("validation.invalid.registrationNumber"))
                .bind(
                        EducationInfo::getRegistrationNumber,
                        EducationInfo::setRegistrationNumber
                );
    }

    private void bindExamScoreField() {

        binder.forField(examScore)
                .withValidator(value -> {
                    if (TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(ortType.getValue())) {
                        return value != null && !value.trim().isEmpty();
                    }
                    return true;
                }, TranslationUtils.getTranslation("validation.required.examScore"))
                .withConverter(new Converter<String, Double>() {
                    @Override
                    public Result<Double> convertToModel(String value, ValueContext context) {

                        if (value == null || value.isEmpty()) {
                            return Result.ok(0.0);
                        }

                        try {
                            return Result.ok(Double.parseDouble(value));
                        } catch (NumberFormatException e) {
                            return Result.error("Invalid number");
                        }
                    }

                    @Override
                    public String convertToPresentation(Double value, ValueContext context) {
                        return value != null ? String.valueOf(value) : "";
                    }
                })
                .bind(
                        EducationInfo::getExamScore,
                        EducationInfo::setExamScore
                );
    }

    private void bindIssuedDateField() {

        binder.forField(issuedDate)
                .withValidator(date -> {
                    if (TranslationUtils.getTranslation("educationInfo.ortType.attended").equals(ortType.getValue())) {
                        return date != null;
                    }
                    return true;
                }, TranslationUtils.getTranslation("validation.required.issuedDate"))
                .bind(
                        EducationInfo::getIssueDate,
                        EducationInfo::setIssueDate
                );
    }

}
