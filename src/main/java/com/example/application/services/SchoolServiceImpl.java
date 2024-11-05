package com.example.application.services;

import com.example.application.entity.School;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.SchoolRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;

    @Autowired
    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public List<School> findAll() {
        return schoolRepository.findAll();
    }

    @Override
    public School findById(Long id) throws BusinessException {

        Optional<School> school = schoolRepository.findById(id);

        if (school.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.school-not-found"));
        }

        return school.get();
    }

    @Override
    public List<School> getSchoolByAddress(Long addressId) {
        return schoolRepository.findByAddress_Id(addressId);
    }
}
