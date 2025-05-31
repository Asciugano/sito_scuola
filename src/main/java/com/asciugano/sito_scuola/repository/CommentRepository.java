package com.asciugano.sito_scuola.repository;

import com.asciugano.sito_scuola.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByAssignmentId(Long assignmentId);
}
