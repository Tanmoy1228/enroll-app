package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.services.*;
import org.springframework.stereotype.Component;

@Component
public class AddressComponentFactory {

    private final CountryService countryService;

    private final ProvinceService provinceService;

    private final RegionService regionService;

    private final CityService cityService;

    private final AddressInfoService addressInfoService;

    public AddressComponentFactory(CountryService countryService,
                                   ProvinceService provinceService,
                                   RegionService regionService,
                                   CityService cityService,
                                   AddressInfoService addressInfoService) {

        this.countryService = countryService;
        this.provinceService = provinceService;
        this.regionService = regionService;
        this.cityService = cityService;
        this.addressInfoService = addressInfoService;
    }

    public AddressComponent createAddressComponent(AddressType addressType) {
        return new AddressComponent(addressType, countryService, provinceService, regionService, cityService, addressInfoService);
    }
}
