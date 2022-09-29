package com.metadata.school.school.course;

import com.metadata.school.school.student.Student;
import com.metadata.school.school.student.StudentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/course")
public class CourseController {
    private final ModelMapper modelMapper;
    private final CourseService courseService;

    public CourseController(ModelMapper modelMapper, CourseService courseService) {
        this.modelMapper = modelMapper;
        this.courseService = courseService;
    }

    @GetMapping(path = "/all-relationships")
    public ResponseEntity<?> getAllCoursesAndStudents(){
        List<Course> coursesAndStudents = courseService.getCoursesWithStudents();

        List<CourseDTO> courseDTOList = coursesAndStudents.stream().map(c -> modelMapper.map(c, CourseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveCourse(@Valid @RequestBody CourseDTO courseDTO){
        Course course = modelMapper.map(courseDTO, Course.class);
        course.setId(UUID.randomUUID());

        Course savedCourse = courseService.saveCourse(course);

        CourseDTO courseDTOReturn = modelMapper.map(savedCourse, CourseDTO.class);
        return new ResponseEntity<>(courseDTOReturn, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateCourse(@PathVariable UUID id, @Valid @RequestBody CourseDTO courseDTO) {
        Course course = modelMapper.map(courseDTO, Course.class);
        course.setId(id);

        Course savedCourse = courseService.updateCourse(course);

        CourseDTO courseDTOReturn = modelMapper.map(savedCourse, CourseDTO.class);
        return new ResponseEntity<>(courseDTOReturn, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourse(@PathVariable UUID id){
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{courseId}/students")
    public ResponseEntity<?> getStudents(@PathVariable UUID courseId){
        List<Student> students = courseService.getStudentsByCourse(courseId);

        List<StudentDTO> studentDTOList = students.stream().map(s -> modelMapper.map(s, StudentDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @PostMapping(path = "/{courseId}/enroll-student/{studentId}")
    public ResponseEntity<?> enrollStudent(@PathVariable UUID courseId, @PathVariable UUID studentId){
        courseService.enrollStudent(courseId, studentId);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/without-student")
    public ResponseEntity<?> getCoursesWithoutStudent(){
        List<Course> coursesWithoutStudent = courseService.getCoursesWithoutStudent();

        List<CourseDTO> courseDTOList = coursesWithoutStudent.stream().map(c -> modelMapper.map(c, CourseDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(courseDTOList, HttpStatus.OK);
    }

}
