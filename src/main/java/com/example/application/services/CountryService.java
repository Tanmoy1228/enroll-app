package com.example.application.services;

import com.example.application.entity.Country;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface CountryService {

    List<Country> findAll();

    Country findById(Long id) throws BusinessException;
}
