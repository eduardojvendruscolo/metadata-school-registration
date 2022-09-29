package com.metadata.school.school.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.metadata.school.school.course.CourseDTO;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
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

}
