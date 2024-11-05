package com.example.application.services;

import com.example.application.entity.School;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface SchoolService {

    List<School> findAll();

    School findById(Long id) throws BusinessException;

    List<School> getSchoolByAddress(Long addressId);
}
