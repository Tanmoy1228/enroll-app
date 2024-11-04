package com.example.application.services;

import com.example.application.entity.Country;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.CountryRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> findAll() {
        return countryRepository.findAll();
    }

    @Override
    public Country findById(Long id) throws BusinessException {

        Optional<Country> country = countryRepository.findById(id);

        if (country.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.country-not-found"));
        }

        return country.get();
    }
}
