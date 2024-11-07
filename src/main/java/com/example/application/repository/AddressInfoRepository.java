package com.example.application.repository;

import com.example.application.entity.AddressInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressInfoRepository extends JpaRepository<AddressInfo, Long> {

    AddressInfo findAddressInfoByEmailAndType(String email, String type);

    List<AddressInfo> findAddressInfoByEmail(String email);
}
