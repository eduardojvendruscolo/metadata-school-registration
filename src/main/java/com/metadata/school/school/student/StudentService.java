package com.metadata.school.school.student;

import com.metadata.school.base.CustomException;
import com.metadata.school.school.course.Course;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final MessageSource messageSource;

    public StudentService(StudentRepository studentRepository, MessageSource messageSource) {
        this.studentRepository = studentRepository;
        this.messageSource = messageSource;
    }

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }

    public List<Student> getStudentsWithCourses(){
        return studentRepository.findAll();
    }

    public Student updateStudent(Student student) {

        boolean studentNotExists = studentRepository.findById(student.getId()).isEmpty();

        if (studentNotExists) {
            String message = messageSource.getMessage("student.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, student.getId()));
        }

        return studentRepository.save(student);
    }

    public void deleteStudent(UUID id) {

        boolean studentNotExists = studentRepository.findById(id).isEmpty();

        if (studentNotExists) {
            String message = messageSource.getMessage("student.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, id));
        }

        studentRepository.deleteById(id);
    }

    public Optional<Student> getStudentById(UUID id) {
        return studentRepository.findById(id);
    }

    public List<Course> getCoursesByStudent(UUID studentId) {
        return studentRepository.coursesByStudent(studentId);
    }

    public List<Student> getStudentsWithoutCourse(){
        return studentRepository.studentsWithoutCourse();
    }
}
