package com.metadata.school.school.student;

import com.metadata.school.school.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends JpaRepository<Student, UUID> {

    @Query(value = "select count(c) from Student s join s.courses c where s.id = :studentId")
    Long enrollmentCount(UUID studentId);

    @Query(value = "select c from Student s join s.courses c where s.id = :studentId")
    List<Course> coursesByStudent(UUID studentId);

    @Query(value = "select s from Student s where not exists (select c from Course c join c.students s2 where s.id = s2.id)")
    List<Student> studentsWithoutCourse();

    @Query(value = "select c from Student s join s.courses c where s.id = :studentId and c.id = :courseId")
    Course courseByStudentAndCourse(UUID studentId, UUID courseId);

}
