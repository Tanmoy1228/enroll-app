package com.example.application.services;

import com.example.application.entity.Region;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface RegionService {

    Region findById(Long id) throws BusinessException;

    List<Region> getRegionByProvinceId(Long provinceId);
}
