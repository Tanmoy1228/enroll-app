package com.example.application.services;

import com.example.application.entity.Language;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface LanguageService {

    List<Language> findAll();

    Language findById(Long id) throws BusinessException;
}
