package com.example.application.services;

import com.example.application.entity.AttestatType;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.AttestatTypeRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttestatTypeServiceImpl implements AttestatTypeService {

    private final AttestatTypeRepository attestatTypeRepository;

    @Autowired
    public AttestatTypeServiceImpl(AttestatTypeRepository attestatTypeRepository) {
        this.attestatTypeRepository = attestatTypeRepository;
    }

    @Override
    public List<AttestatType> findAll() {
        return attestatTypeRepository.findAll();
    }

    @Override
    public AttestatType findById(Long id) throws BusinessException {

        Optional<AttestatType> attestatType = attestatTypeRepository.findById(id);

        if (attestatType.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.attestat-type-not-found"));
        }

        return attestatType.get();
    }
}
