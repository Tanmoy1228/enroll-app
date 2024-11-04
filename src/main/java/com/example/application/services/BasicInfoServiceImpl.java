package com.example.application.services;

import com.example.application.entity.BasicInfo;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.BasicInfoRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicInfoServiceImpl implements BasicInfoService {

    private final BasicInfoRepository basicInfoRepository;

    @Autowired
    public BasicInfoServiceImpl(BasicInfoRepository basicInfoRepository) {
        this.basicInfoRepository = basicInfoRepository;
    }

    @Override
    public void save(BasicInfo basicInfo) {
        basicInfoRepository.save(basicInfo);
    }

    @Override
    public BasicInfo findBasicInfoByEmail(String email) {

        BasicInfo basicInfo = basicInfoRepository.findByEmail(email);

        if (basicInfo == null) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.basic-info-not-found"));
        }

        return basicInfo;
    }
}
