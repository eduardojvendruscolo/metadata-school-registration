package com.metadata.school.school.student;

import com.metadata.school.base.BaseEntity;
import com.metadata.school.school.course.Course;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "student")
@Getter @Setter
public class Student extends BaseEntity implements Serializable {

    private String name;
    private Date birthDate;
    private String document;
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

}
