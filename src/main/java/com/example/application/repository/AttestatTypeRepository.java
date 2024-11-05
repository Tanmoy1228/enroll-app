package com.example.application.repository;

import com.example.application.entity.AttestatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttestatTypeRepository extends JpaRepository<AttestatType, Long> {
}
