package com.metadata.school.school.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.metadata.school.school.student.StudentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class CourseDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private UUID id;

    @NotNull
    @Size(min = 3, max = 255, message = "{course.name.size.error}")
    private String name;

    @NotNull
    @Size(min = 3, max = 255, message = "{course.description.size.error}")
    private String description;

    @NotNull(message = "{course.start.date.empty}")
    private Date startDate;

    @NotNull(message = "{course.end.date.empty}")
    private Date endDate;

    @JsonIgnoreProperties("courses")
    private Set<StudentDTO> students;

}
