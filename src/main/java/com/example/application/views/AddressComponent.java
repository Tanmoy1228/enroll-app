package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.entity.*;
import com.example.application.services.CityService;
import com.example.application.services.CountryService;
import com.example.application.services.ProvinceService;
import com.example.application.services.RegionService;
import com.example.application.utils.TranslationUtils;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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

    private AddressType addressType;

    private AddressInfo addressInfo;

    private final Button saveButton = new Button();

    private boolean isSavedOperation = false;

    private final CountryService countryService;

    private final ProvinceService provinceService;

    private final RegionService regionService;

    private final CityService cityService;

    private static List<Country> loadedCountries = new ArrayList<>();

    private final Binder<AddressInfo> binder = new Binder<>(AddressInfo.class);

    public AddressComponent(CountryService countryService, ProvinceService provinceService, RegionService regionService, CityService cityService) {
        this.countryService = countryService;
        this.provinceService = provinceService;
        this.regionService = regionService;
        this.cityService = cityService;

        addAttachListener(event -> initializeDate());
    }

    public void setTitle(AddressType title) {
        this.addressType = title;
        removeAll();
        add(createAddressForm(title.getCode()));
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

    }

    private void setupBinder() {

    }
}
