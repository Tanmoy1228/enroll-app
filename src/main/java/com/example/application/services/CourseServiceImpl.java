package com.example.application.services;

import com.example.application.entity.Course;
import com.example.application.entity.Faculty;
import com.example.application.exceptions.BusinessException;
import com.example.application.repository.CourseRepository;
import com.example.application.utils.TranslationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) throws BusinessException {

        Optional<Course> course = courseRepository.findById(id);

        if (course.isEmpty()) {
            throw new BusinessException(TranslationUtils.getTranslation("exception.course-not-found"));
        }

        return course.get();
    }

    @Override
    public Map<Faculty, List<Course>> getCoursesByLevel(Long levelId) throws BusinessException {

        List<Object[]> results = courseRepository.getCoursesByFaculty(levelId);

        Map<Faculty, List<Course>> facultyCoursesMap = new HashMap<>();

        for (Object[] result : results) {
            Faculty faculty = (Faculty) result[0];
            Course course = (Course) result[1];
            facultyCoursesMap.computeIfAbsent(faculty, k -> new ArrayList<>()).add(course);
        }

        return facultyCoursesMap;
    }
}
