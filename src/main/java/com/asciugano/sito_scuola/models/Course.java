package com.asciugano.sito_scuola.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    @JsonIgnoreProperties({"teachingCourses", "enrollesCourses", "password"})
    private User teacher;

    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    @JsonIgnoreProperties({"teachingCourses", "enrollesCourses", "password", "role"})
    private Set<User> students = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private List<Assignment> assignments = new ArrayList<Assignment>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getTeacher() { return teacher; }

    public void setTeacher(User teacher) { this.teacher = teacher; }

    public Set<User> getStudents() { return students; }

    public void setStudents(Set<User> students) { this.students = students; }

    public List<Assignment> getAssignments() { return assignments; }

    public void setAssignments(List<Assignment> assignments) { this.assignments = assignments; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}