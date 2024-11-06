package com.example.application.services;

import com.example.application.entity.City;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.CityRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    public City findById(Long id) throws BusinessException {

        Optional<City> city = cityRepository.findById(id);

        if (city.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.city-not-found"));
        }

        return city.get();
    }

    @Override
    public List<City> getCityByRegionId(Long regionId) {
        return cityRepository.findAllByRegionId(regionId);
    }
}
