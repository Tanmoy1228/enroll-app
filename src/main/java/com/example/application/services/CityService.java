package com.example.application.services;

import com.example.application.entity.City;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface CityService {

    City findById(Long id) throws BusinessException;

    List<City> getCityByRegionId(Long regionId);
}
