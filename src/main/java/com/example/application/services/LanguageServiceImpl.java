package com.example.application.services;

import com.example.application.entity.Language;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.LanguageRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    @Autowired
    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Language findById(Long id) throws BusinessException {

        Optional<Language> language = languageRepository.findById(id);

        if (language.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.language-not-found"));
        }

        return language.get();
    }
}
