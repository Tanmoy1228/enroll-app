package com.example.application.repository;

import com.example.application.entity.EducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface EducationInfoRepository extends JpaRepository<EducationInfo, Long> {

    @Transactional(readOnly = true)
    EducationInfo findByEmail(String email);
}
