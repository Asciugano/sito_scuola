package com.asciugano.sito_scuola.repository;

import com.asciugano.sito_scuola.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoursesRepository extends JpaRepository<Course, Long> {
}
