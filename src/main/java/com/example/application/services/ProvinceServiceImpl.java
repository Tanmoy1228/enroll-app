package com.example.application.services;

import com.example.application.entity.Province;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.ProvinceRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceServiceImpl(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }

    @Override
    public Province findById(Long id) throws BusinessException {

        Optional<Province> city = provinceRepository.findById(id);

        if (city.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.province-not-found"));
        }

        return city.get();
    }

    @Override
    public List<Province> getProvinceByCountryId(Long countryId) {
        return provinceRepository.findAllByCountryId(countryId);
    }
}
