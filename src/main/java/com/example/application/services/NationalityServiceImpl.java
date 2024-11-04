package com.example.application.services;

import com.example.application.entity.Nationality;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.NationalityRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NationalityServiceImpl implements NationalityService {

    private final NationalityRepository nationalityRepository;

    public NationalityServiceImpl(NationalityRepository nationalityRepository) {
        this.nationalityRepository = nationalityRepository;
    }

    @Override
    public List<Nationality> findAll() {
        return nationalityRepository.findAll();
    }

    @Override
    public Nationality findById(Long id) throws BusinessException {

        Optional<Nationality> nationality = nationalityRepository.findById(id);

        if (nationality.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.nationality-not-found"));
        }

        return nationality.get();
    }
}
