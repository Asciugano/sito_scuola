package com.asciugano.sito_scuola.controllers;

import com.asciugano.sito_scuola.models.Course;
import com.asciugano.sito_scuola.models.Role;
import com.asciugano.sito_scuola.repository.CoursesRepository;
import com.asciugano.sito_scuola.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CoursesRepository coursesRepository;
    private final JwtService jwtService;

    public CourseController(CoursesRepository coursesRepository, JwtService jwtService) {
        this.coursesRepository = coursesRepository;
        this.jwtService = jwtService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return ResponseEntity.ok(coursesRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        return ResponseEntity.ok(course);
    }

//    @PreAuthorize("hasRole('ADMIN') or hasRole('TEACHER')")
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestHeader("Authorization") String authHeader, @RequestBody Course course) {
        return ResponseEntity.ok(coursesRepository.save(course));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Course course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        course.setAssignments(updatedCourse.getAssignments());
        course.setStudents(updatedCourse.getStudents());
        course.setTeacher(updatedCourse.getTeacher());

        return ResponseEntity.ok(coursesRepository.save(course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable Long id) {
        Course course = coursesRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        coursesRepository.delete(course);

        return ResponseEntity.ok(course);
    }
}
