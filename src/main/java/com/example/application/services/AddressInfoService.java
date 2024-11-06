package com.example.application.services;

import com.example.application.entity.AddressInfo;

public interface AddressInfoService {

    void save(AddressInfo addressInfo);

    AddressInfo findAddressInfoByEmailAndType(String email, String type);
}
