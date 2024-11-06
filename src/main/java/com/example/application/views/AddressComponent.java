package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.entity.*;
import com.example.application.services.*;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.server.VaadinSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AddressComponent extends AbstractFormView {

    private static final Logger LOGGER = LogManager.getLogger(AddressComponent.class);

    private final ComboBox<Country> country = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.country"));

    private final ComboBox<Province> province = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.province"));

    private final ComboBox<Region> region = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.region"));

    private final ComboBox<City> city = createComboBox(TranslationUtils.getTranslation("addressInfo.placeholder.city"));

    private final TextField addressLine = createTextField(TranslationUtils.getTranslation("addressInfo.placeholder.addressLine"));

    private Button saveButton;

    private AddressInfo addressInfo;

    private boolean viewOnlyMode = false;

    private final AddressType addressType;

    private boolean isSavedOperation = false;

    private final CountryService countryService;

    private final ProvinceService provinceService;

    private final RegionService regionService;

    private final CityService cityService;

    private final AddressInfoService addressInfoService;

    private static List<Country> loadedCountries = new ArrayList<>();

    private final Binder<AddressInfo> binder = new Binder<>(AddressInfo.class);

    public AddressComponent(AddressType addressType,
                            CountryService countryService,
                            ProvinceService provinceService,
                            RegionService regionService,
                            CityService cityService,
                            AddressInfoService addressInfoService) {

        this.addressType = addressType;
        this.countryService = countryService;
        this.provinceService = provinceService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.addressInfoService = addressInfoService;

        add(createAddressForm(addressType.getCode()));

        addAttachListener(event -> initializeDate());
    }

    private Div createAddressForm(String title) {

        saveButton = createSaveButton();

        VerticalLayout layout = new VerticalLayout(
                setupFormItem(country, TranslationUtils.getTranslation("addressInfo.label.country"), true),
                setupFormItem(province, TranslationUtils.getTranslation("addressInfo.label.province"), true),
                setupFormItem(region, TranslationUtils.getTranslation("addressInfo.label.region"), true),
                setupFormItem(city, TranslationUtils.getTranslation("addressInfo.label.city"), true),
                setupFormItem(addressLine, TranslationUtils.getTranslation("addressInfo.label.addressLine"), true),
                saveButton
        );

        layout.setSpacing(true);
        layout.setPadding(true);
        layout.setWidthFull();

        return createSectionContainer(title, layout);
    }

    @Override
    protected void configureSaveButton(Button saveButton) {

        saveButton.getStyle().set("width", "100px").set("margin-left", "auto");

        saveButton.addClickListener(event -> {
            if (viewOnlyMode) {
                switchToEditMode(saveButton);
            } else {
                boolean success = saveData();
                if (success) {
                    switchToViewMode(saveButton);
                }
            }
        });
    }

    @Override
    protected void configureNextPageButton(Button nextPageButton) {

    }

    private void switchToViewMode(Button saveButton) {
        setFieldsEditable(false);
        saveButton.setText(TranslationUtils.getTranslation("button.label.edit"));
        viewOnlyMode = true;
    }

    private void switchToEditMode(Button saveButton) {
        setFieldsEditable(true);
        saveButton.setText(TranslationUtils.getTranslation("button.label.save"));
        viewOnlyMode = false;
    }

    public boolean isViewOnlyMode() {
        return viewOnlyMode;
    }

    private void setFieldsEditable(boolean editable) {
        country.setReadOnly(!editable);
        province.setReadOnly(!editable);
        region.setReadOnly(!editable);
        city.setReadOnly(!editable);
        addressLine.setReadOnly(!editable);
    }

    private void initializeDate() {

        loadCountries();
        configureCountryChangeListener();
        configureProvinceChangeListener();
        configureRegionChangeListener();

        setupBinderAndLoadData();
    }

    private void loadCountries() {

        if (loadedCountries.isEmpty()) {
            loadedCountries = countryService.findAll();
        }

        country.setItems(loadedCountries);
        country.setItemLabelGenerator(Country::getName);
    }

    private void configureCountryChangeListener() {

        country.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            province.clear();
            region.clear();
            city.clear();

            Country selectedCountry = event.getValue();

            if (selectedCountry != null) {
                province.setItemLabelGenerator(Province::getName);
                province.setItems(provinceService.getProvinceByCountryId(selectedCountry.getId()));
            }
        });
    }

    private void configureProvinceChangeListener() {

        province.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            region.clear();
            city.clear();

            Province selectedProvince = event.getValue();

            if (selectedProvince != null) {
                region.setItemLabelGenerator(Region::getName);
                region.setItems(regionService.getRegionByProvinceId(selectedProvince.getId()));
            }
        });
    }

    private void configureRegionChangeListener() {

        region.addValueChangeListener(event -> {

            if (isSavedOperation) {
                return;
            }

            city.clear();

            Region selectedRegion = event.getValue();

            if (selectedRegion != null) {
                city.setItemLabelGenerator(City::getName);
                city.setItems(cityService.getCityByRegionId(selectedRegion.getId()));
            }
        });
    }

    private void setupBinderAndLoadData() {

        CompletableFuture.runAsync(() -> {
            try {
                getUI().ifPresent(ui -> ui.access(() -> {
                    setupBinder();
                    loadAddress();
                }));
            } catch (Exception e) {
                LOGGER.error("Failed to load user data", e);
            }
        });
    }

    private void loadAddress() {

        String userEmail = (String) VaadinSession.getCurrent().getAttribute("email");

        try {
            addressInfo = addressInfoService.findAddressInfoByEmailAndType(userEmail, addressType.name());
            binder.readBean(addressInfo);
            switchToViewMode(saveButton);

        } catch (Exception e) {
            LOGGER.error("Failed to load address info", e);
        }

        if (addressInfo == null) {
            addressInfo = new AddressInfo();
        }
    }

    private boolean saveData() {

        try {

            isSavedOperation = true;

            binder.writeBean(addressInfo);

            addressInfo.setType(addressType.name());
            addressInfo.setEmail((String) VaadinSession.getCurrent().getAttribute("email"));

            addressInfoService.save(addressInfo);
            Notification.show(TranslationUtils.getTranslation("notification.dataSavedSuccessfully"), 3000, Notification.Position.TOP_CENTER);

            isSavedOperation = false;

            return true;

        } catch (ValidationException e) {
            Notification.show(TranslationUtils.getTranslation("notification.fillFieldsCorrectly"), 3000, Notification.Position.TOP_CENTER);
        } catch (Exception e) {
            Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
            LOGGER.error(e.getMessage(), e);
        }

        return false;
    }

    private void setupBinder() {
        bindCountryField();
        bindProvinceField();
        bindRegionField();
        bindCityField();
        bindAddressLineField();
    }

    private void bindCountryField() {

        binder.forField(country)
                .asRequired(TranslationUtils.getTranslation("validation.required.country"))
                .withConverter(
                        country -> country != null ? country.getId() : null,
                        id -> id != null ? countryService.findById(id) : null
                )
                .bind(AddressInfo::getCountryId, AddressInfo::setCountryId);
    }

    private void bindProvinceField() {

        binder.forField(province)
                .asRequired(TranslationUtils.getTranslation("validation.required.province"))
                .withConverter(
                        province -> province != null ? province.getId() : null,
                        id -> id != null ? provinceService.findById(id) : null
                )
                .bind(AddressInfo::getProvinceId, AddressInfo::setProvinceId);
    }

    private void bindRegionField() {

        binder.forField(region)
                .asRequired(TranslationUtils.getTranslation("validation.required.region"))
                .withConverter(
                        region -> region != null ? region.getId() : null,
                        id -> id != null ? regionService.findById(id) : null
                )
                .bind(AddressInfo::getRegionId, AddressInfo::setRegionId);
    }

    private void bindCityField() {

        binder.forField(city)
                .asRequired(TranslationUtils.getTranslation("validation.required.city"))
                .withConverter(
                        city -> city != null ? city.getId() : null,
                        id -> id != null ? cityService.findById(id) : null
                )
                .bind(AddressInfo::getCityId, AddressInfo::setCityId);
    }

    private void bindAddressLineField() {

        binder.forField(addressLine)
                .asRequired(TranslationUtils.getTranslation("validation.required.addressLine"))
                .withValidator(new StringLengthValidator(
                        TranslationUtils.getTranslation("validation.invalid.addressLine"), 1, 100))
                .bind(AddressInfo::getAddressLine, AddressInfo::setAddressLine);
    }
}
