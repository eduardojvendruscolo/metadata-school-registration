package com.metadata.school.school.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metadata.school.school.course.CourseDTO;
import com.sun.istack.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class StudentDTO {
    private UUID id;
    @NotNull
    @Size(min = 3, max = 255)
    private String name;
    @NotNull
    private Date birthDate;
    @NotNull
    @Size(min = 1, max = 255)
    private String document;
    @NotNull
    @Size(min = 5, max = 255)
    private String phoneNumber;

    @JsonIgnoreProperties("students")
    private Set<CourseDTO> courses;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<CourseDTO> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDTO> courses) {
        this.courses = courses;
    }
}
