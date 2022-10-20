package com.metadata.school.school.student;

import com.metadata.school.school.course.Course;
import com.metadata.school.school.course.CourseDTO;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
    private final ModelMapper modelMapper;
    private final StudentService studentService;

    public StudentController(ModelMapper modelMapper, StudentService studentService) {
        this.modelMapper = modelMapper;
        this.studentService = studentService;
    }

    @GetMapping(path = "/all-relationships")
    public ResponseEntity<?> getAllStudentsAndStudents(){
        List<Student> StudentsAndStudents = studentService.getStudentsWithCourses();

        List<StudentDTO> studentDTOList = StudentsAndStudents.stream().map(c -> modelMapper.map(c, StudentDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentDTO studentDTO){
        Student student = modelMapper.map(studentDTO, Student.class);

        Student savedStudent = studentService.saveStudent(student);

        StudentDTO studentDTOReturn = modelMapper.map(savedStudent, StudentDTO.class);
        return new ResponseEntity<>(studentDTOReturn, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateStudent(@PathVariable UUID id, @Valid @RequestBody StudentDTO studentDTO) {
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setId(id);

        Student savedStudent = studentService.updateStudent(student);

        StudentDTO studentDTOReturn = modelMapper.map(savedStudent, StudentDTO.class);
        return new ResponseEntity<>(studentDTOReturn, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteStudent(@PathVariable UUID id){
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{studentId}/courses")
    public ResponseEntity<?> getCourses(@PathVariable UUID studentId){
        List<Course> students = studentService.getCoursesByStudent(studentId);

        List<CourseDTO> studentDTOList = students.stream().map(s -> modelMapper.map(s, CourseDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

    @GetMapping(path = "/without-course")
    public ResponseEntity<?> getStudentsWithoutCourse(){
        List<Student> studentsWithoutCourse = studentService.getStudentsWithoutCourse();

        List<StudentDTO> studentDTOList = studentsWithoutCourse.stream().map(s -> modelMapper.map(s, StudentDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(studentDTOList, HttpStatus.OK);
    }

}
