package com.example.application.services;

import com.example.application.entity.Level;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.LevelRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }

    @Override
    public Level findById(Long id) throws BusinessException {

        Optional<Level> level = levelRepository.findById(id);

        if (level.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.level-not-found"));
        }

        return level.get();
    }
}
