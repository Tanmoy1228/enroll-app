package com.example.application.services;

import com.example.application.entity.Faculty;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface FacultyService {

    List<Faculty> findAll();

    Faculty findById(Long id) throws BusinessException;
}
