package com.example.application.views;

import com.example.application.dto.ContactType;
import com.example.application.entity.RelativeContact;
import com.example.application.entity.RelativeInfo;
import com.example.application.dto.RelativeType;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

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

    public RelativesInformationView() {

        setSpacing(true);
        setPadding(true);
        setWidth("600px");

        configureRelativeGrid();
        configureAddRelativeButton();
        configureAddRelativeDialog();
        configureRelativeSaveButton();
        configureNextPageButton();

        setRelatives(new ArrayList<>());

        add(new H3(TranslationUtils.getTranslation("relativeInfo.title")), relativeInfoGrid, addRelativeButton, nextPageButton);
    }

    private void configureRelativeGrid() {

        relativeInfoGrid.addColumn(relativeType -> relativeType.getRelationship().getCode())
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.type"));

        relativeInfoGrid.addColumn(RelativeInfo::getName).setHeader(TranslationUtils.getTranslation("relativeInfo.label.name"));

        relativeInfoGrid.addColumn(RelativeInfo::getSurname).setHeader(TranslationUtils.getTranslation("relativeInfo.label.surname"));

        relativeInfoGrid.addColumn(
                relativeContact -> relativeContact.getRelativeContact().getType().getCode() + ':' + relativeContact.getRelativeContact().getContact())
                .setHeader(TranslationUtils.getTranslation("relativeInfo.label.contacts"));

        relativeInfoGrid.addComponentColumn(this::createActionButtons).setHeader(TranslationUtils.getTranslation("relativeInfo.label.actions"));
    }

    private HorizontalLayout createActionButtons(RelativeInfo relativeInfo) {
        Button editButton = new Button(TranslationUtils.getTranslation("button.label.edit"), event -> editRelative(relativeInfo));
        Button deleteButton = new Button(TranslationUtils.getTranslation("button.label.delete"), event -> deleteRelative(relativeInfo));
        return new HorizontalLayout(editButton, deleteButton);
    }

    private void editRelative(RelativeInfo relativeInfo) {


    }

    private void deleteRelative(RelativeInfo relativeInfo) {


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

    private void configureAddRelativeButton() {
        addRelativeButton.getStyle().set("font-size", "18px").set("font-weight", "bold").set("margin-left", "auto");
        addRelativeButton.addClickListener(event -> openAddContactDialog());
    }

    private void openAddContactDialog() {
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

        relativeDialogLayout.setSpacing(true);
        relativeDialogLayout.setPadding(true);
        relativeDialogLayout.setAlignItems(Alignment.BASELINE);

        return relativeDialogLayout;
    }

    private void configureRelativeSaveButton() {

        saveRelativeButton.getStyle().set("width", "300px").set("background-color", "green").set("color", "white").set("margin-left", "auto");

        saveRelativeButton.addClickListener(event -> {

        });
    }

    private void configureNextPageButton() {

        nextPageButton.addClickListener(event -> {

        });

        nextPageButton.getStyle()
                .set("width", "300px")
                .set("height", "50px")
                .set("color", "white")
                .set("margin-left", "100px")
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
        relativeDialogContactGrid.setVisible(false);

        dialogContactLayout.add(new H3(TranslationUtils.getTranslation("contactInfo.title")), dialogContactInputLayout, relativeDialogContactGrid);

        return dialogContactLayout;
    }

    private void relativeDialogContactAdd() {

    }

    private HorizontalLayout relativeDialogContactDeleteAction(RelativeContact relativeContact) {
        Button deleteButton = new Button(TranslationUtils.getTranslation("button.label.delete"), event -> deleteRelativeDialogContact(relativeContact));
        return new HorizontalLayout(deleteButton);
    }

    private void deleteRelativeDialogContact(RelativeContact relativeContact) {

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


}
