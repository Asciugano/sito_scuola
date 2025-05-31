package com.asciugano.sito_scuola.repository;

import com.asciugano.sito_scuola.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findAssignmentByCourseId(Long courseId);
}
