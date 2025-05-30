package com.asciugano.sito_scuola.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToMany(mappedBy = "students")
    private Set<Course> enrollesCourses = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    private Set<Course> teachingCourses = new HashSet<>();

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public Set<Course> getEnrollesCourses() { return enrollesCourses; }

    public void setEnrollesCourses(Set<Course> enrollesCourses) { this.enrollesCourses = enrollesCourses; }

    public Set<Course> getTeachingCourses() { return teachingCourses; }

    public void setTeachingCourses(Set<Course> teachingCourses) { this.teachingCourses = teachingCourses; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}