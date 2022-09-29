package com.metadata.school.school.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metadata.school.school.student.StudentDTO;
import com.sun.istack.NotNull;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

public class CourseDTO {
    private UUID id;
    @NotNull
    @Size(min = 3, max = 255, message = "{age.adult.only}")
    private String name;
    @NotNull
    @Size(min = 3, max = 255)
    private String description;
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;
    @JsonIgnoreProperties("courses")
    private Set<StudentDTO> students;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Set<StudentDTO> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDTO> students) {
        this.students = students;
    }
}
