package com.example.application.services;

import com.example.application.entity.Nationality;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface NationalityService {

    List<Nationality> findAll();

    Nationality findById(Long id) throws BusinessException;
}
