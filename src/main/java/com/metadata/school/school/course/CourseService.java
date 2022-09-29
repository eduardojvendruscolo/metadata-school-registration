package com.metadata.school.school.course;

import com.metadata.school.base.CustomException;
import com.metadata.school.school.student.Student;
import com.metadata.school.school.student.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MessageSource messageSource;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, MessageSource messageSource) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.messageSource = messageSource;
    }

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {

        boolean courseNotExists = courseRepository.findById(course.getId()).isEmpty();

        if (courseNotExists) {
            String message = messageSource.getMessage("course.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, course.getId()));
        }

        return courseRepository.save(course);
    }

    public void deleteCourse(UUID id) {

        boolean courseNotExists = courseRepository.findById(id).isEmpty();

        if (courseNotExists) {
            String message = messageSource.getMessage("course.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, id));
        }

        courseRepository.deleteById(id);
    }

    public Optional<Course> getCourseById(UUID id){
        return courseRepository.findById(id);
    }

    public List<Student> getStudentsByCourse(UUID courseId){
        return courseRepository.studentsByCourse(courseId);
    }

    public List<Course> getCoursesWithoutStudent(){
        return courseRepository.coursesWithoutStudent();
    }

    public List<Course> getCoursesWithStudents(){
        return courseRepository.findAll();
    }

    public void enrollStudent(UUID courseId, UUID studentId) {

        enrollValidation(courseId, studentId);

        Course course = courseRepository.findById(courseId).get();
        Student student = studentRepository.findById(studentId).get();

        course.addStudent(student);
        courseRepository.save(course);
    }

    private void enrollValidation(UUID courseId, UUID studentId) {

        Optional<Course> optCourse = courseRepository.findById(courseId);
        if (!optCourse.isPresent()) {
            String message = messageSource.getMessage("course.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, courseId));
        }

        Optional<Student> optStudent = studentRepository.findById(studentId);
        if (!optStudent.isPresent()) {
            String message = messageSource.getMessage("student.not.found", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, studentId));
        }

        Long studentsCount = courseRepository.studentsCount(optCourse.get().getId());
        if (studentsCount.compareTo(50L) > 0) {
            String message = messageSource.getMessage("course.enrollments.limit", null, Locale.ENGLISH);
            throw new CustomException(message);
        }

        Long enrollmentCount = studentRepository.enrollmentCount(studentId);
        if (enrollmentCount.compareTo(5L) > 0){
            String message = messageSource.getMessage("student.enrolments.limit", null, Locale.ENGLISH);
            throw new CustomException(message);
        }

        Course courseStudent = studentRepository.courseByStudentAndCourse(studentId, courseId);
        if (courseStudent != null) {
            String message = messageSource.getMessage("student.already.enrolled.on.course", null, Locale.ENGLISH);
            throw new CustomException(String.format(message, optStudent.get().getName(), optCourse.get().getName()));
        }
    }

}
