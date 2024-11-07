package com.example.application.services;

import com.example.application.entity.AddressInfo;

import java.util.List;

public interface AddressInfoService {

    void save(AddressInfo addressInfo);

    AddressInfo findAddressInfoByEmailAndType(String email, String type);

    List<AddressInfo> findAddressInfoByEmail(String email);
}
