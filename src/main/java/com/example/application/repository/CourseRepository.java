package com.example.application.repository;

import com.example.application.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT f, c FROM Faculty f JOIN f.courses c WHERE f.level.id = :levelId")
    List<Object[]> getCoursesByFaculty(@Param("levelId") Long levelId);
}
