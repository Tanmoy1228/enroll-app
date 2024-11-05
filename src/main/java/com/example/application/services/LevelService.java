package com.example.application.services;

import com.example.application.entity.Level;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface LevelService {

    List<Level> findAll();

    Level findById(Long id) throws BusinessException;
}
