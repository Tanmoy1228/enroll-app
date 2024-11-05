package com.example.application.services;

import com.example.application.entity.Course;
import com.example.application.entity.Faculty;
import com.example.application.exceptions.BusinessException;

import java.util.List;
import java.util.Map;

public interface CourseService {

    List<Course> findAll();

    Course findById(Long id) throws BusinessException;

    Map<Faculty, List<Course>> getCoursesByLevel(Long levelId);
}
