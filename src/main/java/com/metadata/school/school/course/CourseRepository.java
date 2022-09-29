package com.metadata.school.school.course;

import com.metadata.school.school.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends JpaRepository<Course, UUID> {
    @Query(value = "select count(s) from Course c left join c.students s where c.id = :courseId")
    Long studentsCount(UUID courseId);

    @Query(value = "select s from Course c left join c.students s where c.id = :courseId")
    List<Student> studentsByCourse(UUID courseId);

    @Query(value = "select c from Course c where not exists (select s from Student s join s.courses c2 where c.id = c2.id)")
    List<Course> coursesWithoutStudent();

}
