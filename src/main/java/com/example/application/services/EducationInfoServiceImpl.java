package com.example.application.services;

import com.example.application.entity.EducationInfo;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.EducationInfoRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationInfoServiceImpl implements EducationInfoService {

    private final EducationInfoRepository educationInfoRepository;

    @Autowired
    public EducationInfoServiceImpl(EducationInfoRepository educationInfoRepository) {
        this.educationInfoRepository = educationInfoRepository;
    }

    @Override
    public void save(EducationInfo educationInfo) {
        educationInfoRepository.save(educationInfo);
    }

    @Override
    public EducationInfo findEducationInfoByEmail(String email) {

        EducationInfo educationInfo = educationInfoRepository.findByEmail(email);

        if (educationInfo == null) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.education-info-not-found"));
        }

        return educationInfo;
    }
}
