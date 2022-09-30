package com.metadata.school.school.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.metadata.school.school.course.CourseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class StudentDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    @NotNull
    @Size(min = 3, max = 255, message = "{student.name.size.error}")
    private String name;

    @NotNull(message = "{student.birth.date.empty}")
    private Date birthDate;

    @NotNull
    @Size(min = 1, max = 255, message = "{student.document.size.error}")
    private String document;

    @NotNull
    @Size(min = 5, max = 255, message = "{student.phone.size.error}")
    private String phoneNumber;

    @JsonIgnoreProperties("students")
    private Set<CourseDTO> courses;

}
