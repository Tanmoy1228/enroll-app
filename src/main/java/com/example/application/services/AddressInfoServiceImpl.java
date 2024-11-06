package com.example.application.services;

import com.example.application.entity.AddressInfo;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.AddressInfoRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressInfoServiceImpl implements AddressInfoService {

    private final AddressInfoRepository addressInfoRepository;

    @Autowired
    public AddressInfoServiceImpl(AddressInfoRepository addressInfoRepository) {
        this.addressInfoRepository = addressInfoRepository;
    }

    @Override
    public void save(AddressInfo addressInfo) {
        addressInfoRepository.save(addressInfo);
    }

    @Override
    public AddressInfo findAddressInfoByEmailAndType(String email, String type) {

        AddressInfo addressInfo = addressInfoRepository.findAddressInfoByEmailAndType(email, type);

        if (addressInfo == null) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.address-info-not-found"));
        }

        return addressInfo;
    }
}
