package com.asciugano.sito_scuola.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
public class Submission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;
    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "assignmet_id")
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getFileUrl() { return fileUrl; }

    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }

    public LocalDateTime getSubmittedAt() { return submittedAt; }

    public void setSubmittedAt(LocalDateTime submittedAt) { this.submittedAt = submittedAt; }

    public Assignment getAssignment() { return assignment; }

    public void setAssignment(Assignment assignment) { this.assignment = assignment; }

    public User getStudent() { return student; }

    public void setStudent(User student) { this.student = student; }
}