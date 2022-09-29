package com.metadata.school.school.course;

import com.metadata.school.school.student.Student;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "course")
@Getter @Setter
public class Course {
    
    @Id
    @org.hibernate.annotations.Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
