package com.metadata.school.school.course;

import com.metadata.school.base.BaseEntity;
import com.metadata.school.school.student.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "course")
@Getter @Setter
public class Course extends BaseEntity implements Serializable {

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    public void addStudent(Student student){
        students.add(student);
    }

}
