package com.example.application.views;

import com.example.application.dto.ContactType;
import com.example.application.entity.RelativeContact;
import com.example.application.entity.RelativeInfo;
import com.example.application.dto.RelativeType;
import com.example.application.services.RelativeInfoService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@AnonymousAllowed
@PageTitle("Relatives Info")
@Route(value = "relatives-information", layout = MainLayout.class)
public class RelativesInformationView extends SecuredView {

    private static final Logger LOGGER = LogManager.getLogger(RelativesInformationView.class);

    private final Grid<RelativeInfo> relativeInfoGrid = new Grid<>(RelativeInfo.class, false);
    private final Button addRelativeButton = new Button(TranslationUtils.getTranslation("button.label.add"));
    private final Button nextPageButton = new Button(TranslationUtils.getTranslation("button.label.next-page"));

    private final Dialog addRelativeDialog = new Dialog();

    private final ComboBox<RelativeType> relativeTypeField = AbstractFormView.createComboBox(TranslationUtils.getTranslation("relativeInfo.tooltip.type"));
    private final TextField relativeName = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.name"));
    private final TextField relativeSurname = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.surname"));
    private final TextField relativeNativeName = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.nativeName"));
    private final TextField relativeNativeSurname = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.nativeSurname"));
    private final TextField relativePatronymic = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.patronymic"));
    private final DatePicker relativeBirthdate = new DatePicker();
    private final TextField relativeBranchName = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.branchName"));
    private final TextField relativeWorkPosition = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.workPosition"));
    private final TextField relativeWorkPlace = createTextField(TranslationUtils.getTranslation("relativeInfo.tooltip.workPlace"));

    private final Button saveRelativeButton = new Button(TranslationUtils.getTranslation("button.label.save"));

    private final ComboBox<ContactType> contactTypeField = new ComboBox<>(TranslationUtils.getTranslation("contactInfo.label.type"));
    private final TextField contactField = new TextField(TranslationUtils.getTranslation("contactInfo.label.contacts"));
    private final Button addContactButton = new Button(TranslationUtils.getTranslation("contactInfo.button.add"));

    private final Grid<RelativeContact> relativeDialogContactGrid = new Grid<>(RelativeContact.class, false);

    private RelativeInfo currentRelative = null;
    private final RelativeInfoService relativeInfoService;
    private List<RelativeInfo> relativeInfos = new ArrayList<>();
    private final Binder<RelativeInfo> binder = new Binder<>(RelativeInfo.class);

    public RelativesInformationView(RelativeInfoService relativeInfoService) {

        this.relativeInfoService = relativeInfoService;

        setSpacing(true);
        setPadding(true);
        setWidth("800px");

        configureRelativeGrid();
        configureAddRelativeButton();
        configureAddRelativeDialog();
        configureRelativeSaveButton();
        configureNextPageButton();

        setRelatives(new ArrayList<>());

        add(new H3(TranslationUtils.getTranslation("relativeInfo.title")), relativeInfoGrid, addRelativeButton, nextPageButton);

        addAttachListener(event -> initializeData());
    }

    private void configureRelativeGrid() {

        relativeInfoGrid.addColumn(relative -> relative.getRelationship().getCode())
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.type")).setWidth("100px").setResizable(true);

        relativeInfoGrid.addColumn(RelativeInfo::getName)
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.name")).setWidth("150px").setResizable(true);

        relativeInfoGrid.addColumn(RelativeInfo::getSurname)
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.surname")).setWidth("100px").setResizable(true);

        relativeInfoGrid.addColumn(
                relative -> relative == null ? null : relative.getRelativeContacts().get(0).getType().getCode() + ':' + relative.getRelativeContacts().get(0).getContact())
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.contacts")).setWidth("200px").setResizable(true);

        relativeInfoGrid.addComponentColumn(this::createActionButtons)
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.actions")).setWidth("200px").setResizable(true);
    }

    private HorizontalLayout createActionButtons(RelativeInfo relativeInfo) {
        Button editButton = new Button(TranslationUtils.getTranslation("button.label.edit"), event -> editRelative(relativeInfo));
        Button deleteButton = new Button(TranslationUtils.getTranslation("button.label.delete"), event -> deleteRelative(relativeInfo));
        return new HorizontalLayout(editButton, deleteButton);
    }

    private void editRelative(RelativeInfo relativeInfo) {

        currentRelative = relativeInfo;
        binder.readBean(currentRelative);

        relativeTypeField.setValue(relativeInfo.getRelationship());
        relativeName.setValue(relativeInfo.getName());
        relativeSurname.setValue(relativeInfo.getSurname());
        relativeNativeName.setValue(relativeInfo.getNativeName());
        relativeNativeSurname.setValue(relativeInfo.getNativeSurname());
        relativePatronymic.setValue(relativeInfo.getPatronymic());
        relativeBirthdate.setValue(relativeInfo.getBirthDate());
        relativeBranchName.setValue(relativeInfo.getBranch());
        relativeWorkPosition.setValue(relativeInfo.getWorkPosition());
        relativeWorkPlace.setValue(relativeInfo.getWorkPlace());

        setRelativeContacts(relativeInfo.getRelativeContacts());

        contactTypeField.clear();
        contactField.clear();

        addRelativeDialog.open();
    }

    private void deleteRelative(RelativeInfo relativeInfo) {

        try {
            relativeInfoService.deleteById(relativeInfo.getId());
            relativeInfos.remove(relativeInfo);
            setRelatives(relativeInfos);
        } catch (Exception e) {
            Notification.show(TranslationUtils.getTranslation("notification.relativeNotDelete"), 1000, Notification.Position.TOP_CENTER);
        }
    }

    public void setRelatives(List<RelativeInfo> relativeInfos) {

        int rowHeight = 55;
        int baseHeight = 60;
        int contactCount = 0;

        if (relativeInfos != null) {
            contactCount = Math.min(10, relativeInfos.size());
        }

        int calculatedHeight = baseHeight + (contactCount * rowHeight);

        String gridHeight = calculatedHeight + "px";
        relativeInfoGrid.setHeight(gridHeight);

        if (relativeInfos == null || relativeInfos.isEmpty()) {
            return;
        }

        relativeInfoGrid.setItems(relativeInfos);
    }

    public void setRelativeContacts(List<RelativeContact> relativeContacts) {

        int rowHeight = 55;
        int contactCount = 0;

        if (relativeContacts != null) {
            contactCount = Math.min(5, relativeContacts.size());
        }

        int calculatedHeight = contactCount * rowHeight;

        String gridHeight = calculatedHeight + "px";
        relativeDialogContactGrid.setHeight(gridHeight);

        if (relativeContacts == null || relativeContacts.isEmpty()) {
            return;
        }

        relativeDialogContactGrid.setItems(relativeContacts);
    }

    private void configureAddRelativeButton() {
        addRelativeButton.getStyle().set("font-size", "18px").set("font-weight", "bold").set("margin-left", "auto");
        addRelativeButton.addClickListener(event -> openAddContactDialog());
    }

    private void openAddContactDialog() {

        currentRelative = new RelativeInfo();
        currentRelative.setRelativeContacts(new ArrayList<>());

        binder.readBean(currentRelative);

        clearDialogFields();
        addRelativeDialog.open();
    }

    private void clearDialogFields() {

        relativeTypeField.clear();
        relativeName.clear();
        relativeSurname.clear();
        relativeNativeName.clear();
        relativeNativeSurname.clear();
        relativePatronymic.clear();
        relativeBirthdate.clear();
        relativeBranchName.clear();
        relativeWorkPosition.clear();
        relativeWorkPlace.clear();
        contactTypeField.clear();
        contactField.clear();
    }

    private void configureAddRelativeDialog() {

        VerticalLayout dialogLayout = configureAddRelativeDialogLayout();

        addRelativeDialog.setWidth("600px");
        addRelativeDialog.add(dialogLayout);
        dialogLayout.setAlignItems(Alignment.BASELINE);
    }

    private VerticalLayout configureAddRelativeDialogLayout() {

        VerticalLayout relativeDialogLayout = new VerticalLayout(
                setupFormItem(relativeTypeField, TranslationUtils.getTranslation("relativeInfo.label.type"), true),
                setupFormItem(relativeName, TranslationUtils.getTranslation("relativeInfo.label.name"), true),
                setupFormItem(relativeSurname, TranslationUtils.getTranslation("relativeInfo.label.surname"), true),
                setupFormItem(relativeNativeName, TranslationUtils.getTranslation("relativeInfo.label.nativeName"), true),
                setupFormItem(relativeNativeSurname, TranslationUtils.getTranslation("relativeInfo.label.nativeSurname"), true),
                setupFormItem(relativePatronymic, TranslationUtils.getTranslation("relativeInfo.label.patronymic"), false),
                setupFormItem(relativeBirthdate, TranslationUtils.getTranslation("relativeInfo.label.dateOfBirth"), false),
                setupFormItem(relativeBranchName, TranslationUtils.getTranslation("relativeInfo.label.branchName"), false),
                setupFormItem(relativeWorkPosition, TranslationUtils.getTranslation("relativeInfo.label.workPosition"), false),
                setupFormItem(relativeWorkPlace, TranslationUtils.getTranslation("relativeInfo.label.workPlace"), false),
                createDialogContactLayout(),
                saveRelativeButton
        );

        relativeTypeField.setItems(RelativeType.values());
        relativeTypeField.setItemLabelGenerator(RelativeType::getCode);

        relativeDialogLayout.setSpacing(true);
        relativeDialogLayout.setPadding(true);
        relativeDialogLayout.setAlignItems(Alignment.BASELINE);

        return relativeDialogLayout;
    }

    private void configureRelativeSaveButton() {

        saveRelativeButton.getStyle().set("width", "300px").set("background-color", "green").set("color", "white").set("margin-left", "auto");

        saveRelativeButton.addClickListener(event -> handleSaveRelative());
    }

    private void handleSaveRelative() {

        int relativeDialogContactSize = relativeDialogContactGrid.getDataProvider().fetch(new Query<>()).toList().size();

        if (relativeDialogContactSize == 0) {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 1000, Notification.Position.TOP_CENTER);
            return;
        }

        if (binder.writeBeanIfValid(currentRelative)) {

            currentRelative.setEmail((String) VaadinSession.getCurrent().getAttribute("email"));

            try {
                relativeInfoService.save(currentRelative);

                if (!relativeInfos.contains(currentRelative)) {
                    relativeInfos.add(currentRelative);
                } else {
                    relativeInfoGrid.getDataProvider().refreshItem(currentRelative);
                }
                Notification.show(TranslationUtils.getTranslation("notification.dataSavedSuccessfully"), 1000, Notification.Position.TOP_CENTER);

            } catch (Exception e) {
                Notification.show(TranslationUtils.getTranslation("notification.contactNotSaved"), 1000, Notification.Position.TOP_CENTER);
            }

            setRelatives(relativeInfos);

            clearDialogFields();
            addRelativeDialog.close();
        } else {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 1000, Notification.Position.TOP_CENTER);
        }
    }

    private void configureNextPageButton() {

        nextPageButton.addClickListener(event -> {

            if (relativeInfos != null && !relativeInfos.isEmpty()) {
                VaadinSession.getCurrent().setAttribute("selectedTabIndex", 4);
                getUI().ifPresent(ui -> ui.navigate(ApplyView.class));
            } else {
                Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 1000, Notification.Position.TOP_CENTER);
            }
        });

        nextPageButton.getStyle()
                .set("width", "200px")
                .set("height", "50px")
                .set("color", "white")
                .set("margin-left", "300px")
                .set("margin-bottom", "200px")
                .set("background-color", "#007bff");
    }

    private VerticalLayout createDialogContactLayout() {

        VerticalLayout dialogContactLayout = new VerticalLayout();

        contactTypeField.setItems(ContactType.values());
        contactTypeField.setItemLabelGenerator(ContactType::getCode);
        addContactButton.addClickListener(event -> relativeDialogContactAdd());

        HorizontalLayout dialogContactInputLayout = new HorizontalLayout(contactTypeField, contactField, addContactButton);
        dialogContactInputLayout.setAlignItems(Alignment.BASELINE);

        relativeDialogContactGrid.addColumn(contactType -> contactType.getType().getCode());
        relativeDialogContactGrid.addColumn(RelativeContact::getContact);
        relativeDialogContactGrid.addComponentColumn(this::relativeDialogContactDeleteAction);
        setRelativeContacts(new ArrayList<>());

        dialogContactLayout.add(new H3(TranslationUtils.getTranslation("contactInfo.title")), dialogContactInputLayout, relativeDialogContactGrid);

        return dialogContactLayout;
    }

    private void relativeDialogContactAdd() {

        String currentRelativeContactValue = contactField.getValue();
        ContactType currentRelativeContactType = contactTypeField.getValue();

        RelativeContact relativeContact = new RelativeContact(currentRelativeContactType, currentRelativeContactValue);

        currentRelative.getRelativeContacts().add(relativeContact);
        setRelativeContacts(currentRelative.getRelativeContacts());

        contactTypeField.clear();
        contactField.clear();

    }

    private HorizontalLayout relativeDialogContactDeleteAction(RelativeContact relativeContact) {
        Button deleteButton = new Button(TranslationUtils.getTranslation("button.label.delete"), event -> deleteRelativeDialogContact(relativeContact));
        return new HorizontalLayout(deleteButton);
    }

    private void deleteRelativeDialogContact(RelativeContact relativeContact) {
        currentRelative.getRelativeContacts().remove(relativeContact);
        setRelativeContacts(currentRelative.getRelativeContacts());
    }

    private Span createLabel(String labelText, boolean isRequired) {

        Span label = new Span(labelText);

        label.getStyle()
                .set("width", "150px")
                .set("font-size", "14px")
                .set("font-weight", "bold")
                .set("margin-right", "50px");

        if (isRequired) {

            Span requiredIndicator = new Span("*");
            requiredIndicator.getStyle()
                    .set("color", "red")
                    .set("font-size", "10px")
                    .set("font-weight", "bold")
                    .set("margin-left", "3px");

            label.add(requiredIndicator);
        }

        return label;
    }

    private HorizontalLayout setupFormItem(Component component, String labelText, boolean isRequired) {

        component.getElement().getStyle().set("width", "250px");
        component.getElement().setProperty("required", isRequired);

        Span label = createLabel(labelText, isRequired);

        HorizontalLayout layout = new HorizontalLayout(label, component);
        layout.setAlignItems(Alignment.CENTER);

        return layout;
    }

    private static TextField createTextField(String tooltip) {
        return AbstractFormView.createTextField(tooltip);
    }

    private void initializeData() {
        setupBinderAndLoadData();
    }

    private void setupBinderAndLoadData() {

        CompletableFuture.runAsync(() -> {
            try {
                getUI().ifPresent(ui -> ui.access(() -> {
                    setupBinder();
                    loadRelatives();
                }));
            } catch (Exception e) {
                LOGGER.error("Failed to load user data", e);
            }
        });
    }

    private void loadRelatives() {

        String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");

        relativeInfos = relativeInfoService.findRelativeInfoByEmail(userEmail);
        setRelatives(relativeInfos);
    }

    private void setupBinder() {

        bindRelativeTypeFieldField();
        bindRelativeNameFieldField();
        bindRelativeSurnameFieldField();
        bindRelativeNativeNameFieldField();
        bindRelativeNativeSurnameFieldField();
        bindRelativePatronymicFieldField();
        bindRelativeBirthDateField();
        bindRelativeBranchFieldField();
        bindRelativeWorkPositionFieldField();
        bindRelativeWorkPlaceFieldField();
    }

    private void bindRelativeTypeFieldField() {

        binder.forField(relativeTypeField)
                .asRequired(TranslationUtils.getTranslation("validation.required.type"))
                .bind(RelativeInfo::getRelationship, RelativeInfo::setRelationship);
    }

    private void bindRelativeNameFieldField() {

        binder.forField(relativeName)
                .asRequired(TranslationUtils.getTranslation("validation.required.name"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.name"), 1, 100))
                .bind(RelativeInfo::getName, RelativeInfo::setName);
    }

    private void bindRelativeSurnameFieldField() {

        binder.forField(relativeSurname)
                .asRequired(TranslationUtils.getTranslation("validation.required.surname"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.surname"), 1, 100))
                .bind(RelativeInfo::getSurname, RelativeInfo::setSurname);
    }

    private void bindRelativeNativeNameFieldField() {

        binder.forField(relativeNativeName)
                .asRequired(TranslationUtils.getTranslation("validation.required.nativeName"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.nativeName"), 1, 100))
                .bind(RelativeInfo::getNativeName, RelativeInfo::setNativeName);
    }

    private void bindRelativeNativeSurnameFieldField() {

        binder.forField(relativeNativeSurname)
                .asRequired(TranslationUtils.getTranslation("validation.required.nativeSurname"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.nativeSurname"), 1, 100))
                .bind(RelativeInfo::getNativeSurname, RelativeInfo::setNativeSurname);
    }

    private void bindRelativePatronymicFieldField() {

        binder.forField(relativePatronymic)
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.patronymic"), 0, 100))
                .bind(RelativeInfo::getPatronymic, RelativeInfo::setPatronymic);
    }

    private void bindRelativeBirthDateField() {

        binder.forField(relativeBirthdate)
                .asRequired(TranslationUtils.getTranslation("validation.required.dateOfBirth"))
                .bind(RelativeInfo::getBirthDate, RelativeInfo::setBirthDate);
    }

    private void bindRelativeBranchFieldField() {

        binder.forField(relativeBranchName)
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.branchName"), 0, 100))
                .bind(RelativeInfo::getBranch, RelativeInfo::setBranch);
    }

    private void bindRelativeWorkPositionFieldField() {

        binder.forField(relativeWorkPosition)
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.workPosition"), 0, 100))
                .bind(RelativeInfo::getWorkPosition, RelativeInfo::setWorkPosition);
    }

    private void bindRelativeWorkPlaceFieldField() {

        binder.forField(relativeWorkPlace)
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.workPlace"), 0, 100))
                .bind(RelativeInfo::getWorkPlace, RelativeInfo::setWorkPlace);
    }

}
