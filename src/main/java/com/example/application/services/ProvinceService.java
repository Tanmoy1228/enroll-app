package com.example.application.services;

import com.example.application.entity.Province;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface ProvinceService {

    Province findById(Long id) throws BusinessException;

    List<Province> getProvinceByCountryId(Long countryId);
}
