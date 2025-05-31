package com.asciugano.sito_scuola.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "assignments")
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    @ManyToOne
    @JoinColumn(name = "course_id")
    @JsonIgnoreProperties({"assignments"})
    private Course course;

    @OneToMany(mappedBy = "assignment", cascade = CascadeType.ALL)
    private List<Submission> submissions = new ArrayList<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getDueDate() { return dueDate; }

    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }

    public Course getCourse() { return course; }

    public void setCourse(Course course) { this.course = course; }

    public List<Submission> getSubmissions() { return submissions; }

    public void setSubmissions(List<Submission> submissions) { this.submissions = submissions; }
}