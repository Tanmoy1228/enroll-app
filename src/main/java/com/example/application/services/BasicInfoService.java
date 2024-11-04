package com.example.application.services;

import com.example.application.entity.BasicInfo;

public interface BasicInfoService {

    void save(BasicInfo basicInfo);

    BasicInfo findBasicInfoByEmail(String email);
}
