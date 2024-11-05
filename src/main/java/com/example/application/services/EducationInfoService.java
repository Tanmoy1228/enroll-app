package com.example.application.services;

import com.example.application.entity.EducationInfo;

public interface EducationInfoService {

    void save(EducationInfo basicInfo);

    EducationInfo findEducationInfoByEmail(String email);
}
