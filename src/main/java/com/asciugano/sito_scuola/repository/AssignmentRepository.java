package com.asciugano.sito_scuola.repository;

import com.asciugano.sito_scuola.models.Assignment;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
}
