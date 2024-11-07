package com.example.application.repository;

import com.example.application.entity.RelativeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RelativeInfoRepository extends JpaRepository<RelativeInfo, Long> {

    List<RelativeInfo> findAllByEmail(String email);
}
