package com.example.application.services;

import com.example.application.entity.RelativeInfo;

import java.util.List;

public interface RelativeInfoService {

    void save(RelativeInfo relativeInfo);

    List<RelativeInfo> findRelativeInfoByEmail(String email);

    void deleteById(Long id);
}
