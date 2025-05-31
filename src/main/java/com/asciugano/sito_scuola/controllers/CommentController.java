package com.asciugano.sito_scuola.controllers;

import com.asciugano.sito_scuola.models.Assignment;
import com.asciugano.sito_scuola.models.Comment;
import com.asciugano.sito_scuola.repository.AssignmentRepository;
import com.asciugano.sito_scuola.repository.CommentRepository;
import com.asciugano.sito_scuola.repository.UserRepository;
import com.asciugano.sito_scuola.services.DTO.CommentRequestDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentRepository commentRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;

    public CommentController(CommentRepository commentRepository, AssignmentRepository assignmentRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.assignmentRepository = assignmentRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/assignment/{assignmentId}")
    public List<Comment> getCommentsByAssignment(@PathVariable long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new RuntimeException("Assignment not found"));

        return commentRepository.findAllByAssignmentId(assignmentId);
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequestDTO dto) {
        if(assignmentRepository.findById(dto.getAssignmentId()).orElse(null) != null) {

            Comment comment = new Comment();
            comment.setCreatedAt(LocalDateTime.now());
            comment.setAuthor(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
            comment.setAssignment(assignmentRepository.findById(dto.getAssignmentId()).orElseThrow(() -> new RuntimeException("Assignment not found")));
            comment.setContent(dto.getContent());

            return ResponseEntity.ok(commentRepository.save(comment));
        }

        return ResponseEntity.notFound().build();
    }
}
