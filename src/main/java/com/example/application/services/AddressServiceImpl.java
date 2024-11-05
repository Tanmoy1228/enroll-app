package com.example.application.services;

import com.example.application.entity.Address;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.AddressRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @Override
    public Address findById(Long id) throws BusinessException {

        Optional<Address> address = addressRepository.findById(id);

        if (address.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.address-not-found"));
        }

        return address.get();
    }

    @Override
    public List<Address> getAddressesByCountryId(Long countryId) {
        return addressRepository.findByCountry_Id(countryId);
    }
}
