package com.metadata.school.school.student;

import com.metadata.school.school.course.Course;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter @Setter
public class Student {

    @Id
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private String name;
    private Date birthDate;
    private String document;
    private String phoneNumber;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses;

}
