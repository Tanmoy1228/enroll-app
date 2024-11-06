package com.example.application.views;

import com.example.application.dto.AddressType;
import com.example.application.services.CityService;
import com.example.application.services.CountryService;
import com.example.application.services.ProvinceService;
import com.example.application.services.RegionService;
import org.springframework.stereotype.Component;

@Component
public class AddressComponentFactory {

    private final CountryService countryService;

    private final ProvinceService provinceService;

    private final RegionService regionService;

    private final CityService cityService;

    public AddressComponentFactory(CountryService countryService,
                                   ProvinceService provinceService,
                                   RegionService regionService,
                                   CityService cityService) {

        this.countryService = countryService;
        this.provinceService = provinceService;
        this.regionService = regionService;
        this.cityService = cityService;
    }

    public AddressComponent createAddressComponent(AddressType title) {
        AddressComponent addressComponent = new AddressComponent(countryService, provinceService, regionService, cityService);
        addressComponent.setTitle(title);
        return addressComponent;
    }
}
