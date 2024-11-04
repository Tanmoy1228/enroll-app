package com.example.application.repository;

import com.example.application.entity.BasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BasicInfoRepository extends JpaRepository<BasicInfo, Long> {

    @Transactional(readOnly = true)
    BasicInfo findByEmail(String email);

}
