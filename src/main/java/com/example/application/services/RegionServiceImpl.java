package com.example.application.services;

import com.example.application.entity.Region;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.RegionRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService {

    private final RegionRepository regionRepository;

    @Autowired
    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    public Region findById(Long id) throws BusinessException {

        Optional<Region> city = regionRepository.findById(id);

        if (city.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.region-not-found"));
        }

        return city.get();
    }

    @Override
    public List<Region> getRegionByProvinceId(Long provinceId) {
        return regionRepository.findAllByProvinceId(provinceId);
    }
}
