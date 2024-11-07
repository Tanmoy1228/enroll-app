package com.example.application.services;

import com.example.application.entity.RelativeInfo;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.RelativeInfoRepository;
import com.example.application.utils.TranslationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RelativeInfoServiceImpl implements RelativeInfoService {

    private final Logger LOGGER = LogManager.getLogger(RelativeInfoServiceImpl.class);

    private final RelativeInfoRepository relativeInfoRepository;

    @Autowired
    RelativeInfoServiceImpl(RelativeInfoRepository relativeInfoRepository) {
        this.relativeInfoRepository = relativeInfoRepository;
    }

    @Override
    @Transactional
    public void save(RelativeInfo relativeInfo) {

        relativeInfo.getRelativeContacts().forEach(relativeContact -> relativeContact.setRelativeInfo(relativeInfo));

        relativeInfoRepository.save(relativeInfo);
    }

    @Override
    public List<RelativeInfo> findRelativeInfoByEmail(String email) {
        return relativeInfoRepository.findAllByEmail(email);
    }

    public void deleteById(Long id) {

        Optional<RelativeInfo> relativeInfo = relativeInfoRepository.findById(id);

        if (relativeInfo.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.relative-info-not-found"));
        }

        try {
            relativeInfoRepository.deleteById(id);
        } catch (Exception e) {
            LOGGER.error("Failed to delete relative info. {}", e.getMessage(), e);
            throw new BusinessException(e.getMessage());
        }
    }
}
