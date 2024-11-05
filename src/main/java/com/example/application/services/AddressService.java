package com.example.application.services;

import com.example.application.entity.Address;
import com.example.application.exceptions.BusinessException;

import java.util.List;

public interface AddressService {

    List<Address> findAll();

    Address findById(Long id) throws BusinessException;

    List<Address> getAddressesByCountryId(Long countryId);
}
