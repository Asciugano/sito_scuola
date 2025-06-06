package com.asciugano.sito_scuola.controllers;

import com.asciugano.sito_scuola.models.Assignment;
import com.asciugano.sito_scuola.repository.AssignmentRepository;
import com.asciugano.sito_scuola.repository.CoursesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentsController {

    private final AssignmentRepository assignmentRepository;
    private final CoursesRepository coursesRepository;

    public AssignmentsController(AssignmentRepository assignmentRepository, CoursesRepository coursesRepository) {
        this.assignmentRepository = assignmentRepository;
        this.coursesRepository = coursesRepository;
    }

    @GetMapping("/by-course/{course_id}")
    public ResponseEntity<List<Assignment>> getAssignmentsByCourse(@PathVariable Long course_id) {
        return coursesRepository.findById(course_id)
                .map(course -> ResponseEntity.ok(course.getAssignments()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/course/{courseId}")
    public List<Assignment> getAllAssignmentsByCourse(@PathVariable Long courseId) {
        return assignmentRepository.findAssignmentByCourseId(courseId);
    }

    @PostMapping
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        if(assignment.getCreatedAt() == null)
            assignment.setCreatedAt(LocalDateTime.now());

        Assignment savedAssignment = assignmentRepository.save(assignment);

        URI location = ServletUriComponentsBuilder.
                fromCurrentRequest().
                path("/{id}").
                buildAndExpand(savedAssignment.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedAssignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));

        return ResponseEntity.ok(assignment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable Long id, @RequestBody Assignment updatedAssignment) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));

        assignment.setTitle(updatedAssignment.getTitle());
        assignment.setDescription(updatedAssignment.getDescription());
        assignment.setCreatedAt(updatedAssignment.getCreatedAt());
        assignment.setCourse(updatedAssignment.getCourse());
        assignment.setSubmissions(updatedAssignment.getSubmissions());
        assignment.setDueDate(updatedAssignment.getDueDate());

        assignmentRepository.save(assignment);

        return ResponseEntity.ok(assignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Assignment> deleteAssignment(@PathVariable Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Assignment not found"));
        assignmentRepository.delete(assignment);

        return ResponseEntity.ok(assignment);
    }
}
