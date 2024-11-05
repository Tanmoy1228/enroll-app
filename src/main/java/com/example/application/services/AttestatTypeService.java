package com.example.application.services;

import com.example.application.entity.AttestatType;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface AttestatTypeService {

    List<AttestatType> findAll();

    AttestatType findById(Long id) throws BusinessException;
}
