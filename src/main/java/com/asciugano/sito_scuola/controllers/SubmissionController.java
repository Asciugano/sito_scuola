package com.asciugano.sito_scuola.controllers;

import com.asciugano.sito_scuola.models.Assignment;
import com.asciugano.sito_scuola.models.Submission;
import com.asciugano.sito_scuola.models.User;
import com.asciugano.sito_scuola.repository.AssignmentRepository;
import com.asciugano.sito_scuola.repository.SubmissionsRepository;
import com.asciugano.sito_scuola.repository.UserRepository;
import com.asciugano.sito_scuola.services.DTO.SubmissionRequestDTO;
import com.asciugano.sito_scuola.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    private final SubmissionsRepository submissionsRepository;
    private final AssignmentRepository assignmentRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SubmissionController(SubmissionsRepository SubmissionsRepository, AssignmentRepository AssignmentRepository, JwtService jwtService, UserRepository userRepository) {
        this.submissionsRepository = SubmissionsRepository;
        this.assignmentRepository = AssignmentRepository;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Submission> submitAssignment(@RequestHeader("Authorization") String authHeader, @RequestBody SubmissionRequestDTO dto) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtService.extractUsername(token);
        User student = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Assignment assignment = assignmentRepository.findById(dto.getAssignmentId()).orElseThrow(() -> new RuntimeException("Assignment not found"));

        Submission submission = new Submission();
        submission.setAssignment(assignment);
        submission.setStudent(student);
        submission.setFileUrl(dto.getContent());
        submission.setSubmittedAt(LocalDateTime.now());

        return ResponseEntity.ok(submissionsRepository.save(submission));
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Submission> getSubmissionByAssignmentId(@PathVariable("assignmentId") long assignmentId) {
        return ResponseEntity.ok(submissionsRepository.findById(assignmentId).orElseThrow(() -> new RuntimeException("Submission not found")));
    }
}
