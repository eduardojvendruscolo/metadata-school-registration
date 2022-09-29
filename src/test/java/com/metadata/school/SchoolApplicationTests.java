package com.metadata.school;

import com.metadata.school.base.CustomException;
import com.metadata.school.school.course.Course;
import com.metadata.school.school.course.CourseService;
import com.metadata.school.school.student.Student;
import com.metadata.school.school.student.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class SchoolApplicationTests {

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentService studentService;

	private static PodamFactory podamFactory;

	@BeforeAll
	static void configurePodamFactory() {
		podamFactory = new PodamFactoryImpl();
		podamFactory.getStrategy().setDefaultNumberOfCollectionElements(0);
	}			

	@Test
	void testInsertNewCourse() {
		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		Assertions.assertNotNull(insertedCourse);
		Assertions.assertEquals(course.getId(), insertedCourse.getId());
		Assertions.assertEquals(course.getDescription(), insertedCourse.getDescription());
		Assertions.assertEquals(course.getStartDate(), insertedCourse.getStartDate());
		Assertions.assertEquals(course.getEndDate(), insertedCourse.getEndDate());
	}

	@Test
	void testDeleteCourse() {
		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		courseService.deleteCourse(insertedCourse.getId());

		Optional<Course> optCourse = courseService.getCourseById(insertedCourse.getId());

		Assertions.assertFalse(optCourse.isPresent());
	}

	@Test
	void testUpdateCourse() {
		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		insertedCourse.setName("new course name");

		courseService.updateCourse(insertedCourse);

		Optional<Course> optCourse = courseService.getCourseById(insertedCourse.getId());
		if (optCourse.isPresent()){
			Course updatedCourse = optCourse.get();
			Assertions.assertEquals("new course name", updatedCourse.getName());
		} else
			Assertions.fail();
	}

	@Test
	void testInsertNewStudent() {
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		Assertions.assertNotNull(insertedStudent);
		Assertions.assertEquals(student.getId(), insertedStudent.getId());
		Assertions.assertEquals(student.getName(), insertedStudent.getName());
		Assertions.assertEquals(student.getDocument(), insertedStudent.getDocument());
		Assertions.assertEquals(student.getPhoneNumber(), insertedStudent.getPhoneNumber());
		Assertions.assertEquals(student.getBirthDate(), insertedStudent.getBirthDate());
	}

	@Test
	void testDeleteStudent() {
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		studentService.deleteStudent(insertedStudent.getId());

		Optional<Student> optStudent = studentService.getStudentById(insertedStudent.getId());

		Assertions.assertFalse(optStudent.isPresent());
	}

	@Test
	void testUpdateStudent() {
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		insertedStudent.setName("new student name");

		studentService.updateStudent(insertedStudent);

		Optional<Student> optStudent = studentService.getStudentById(insertedStudent.getId());
		if (optStudent.isPresent()) {
			Student updatedStudent = optStudent.get();
			Assertions.assertEquals("new student name", updatedStudent.getName());
		} else
			Assertions.fail();
	}

	@Test
	void validateMaxCourseEnrollments() {
		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		for (int i = 0; i <= 51; i++) {
			Student student = podamFactory.manufacturePojo(Student.class);
			Student insertedStudent = studentService.saveStudent(student);

			if (i == 51) {
				Exception exception = Assertions.assertThrows(CustomException.class, () ->
					courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId())
				);
				Assertions.assertEquals("The class can't have more than 50 students", exception.getMessage());
			} else
				courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId());
		}
	}

	@Test
	public void testMaxStudentEnrollments() {
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		for (int i = 0; i <= 6; i++) {
			Course course = podamFactory.manufacturePojo(Course.class);
			Course insertedCourse = courseService.saveCourse(course);

			if (i == 6) {
				Exception exception = Assertions.assertThrows(CustomException.class, () ->
					courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId()));

				Assertions.assertEquals("The student can't be enrolled in more than 5 courses", exception.getMessage());
			} else
				courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId());
		}
	}

	@Test
	public void testDuplicateEnrollments(){
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		for (int i = 0; i <= 1; i++) {
			if (i == 1) {
				Exception exception = Assertions.assertThrows(CustomException.class, () ->
						courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId()));

				Assertions.assertEquals(
						String.format("The student %s is already enrolled on course %s", insertedStudent.getName(), insertedCourse.getName()),
						exception.getMessage());
			} else
				courseService.enrollStudent(insertedCourse.getId(), insertedStudent.getId());
		}
	}

	@Test
	public void testInvalidStudentEnrollment() {
		Course course = podamFactory.manufacturePojo(Course.class);
		Course insertedCourse = courseService.saveCourse(course);

		UUID studentId = UUID.randomUUID();
		Exception exception = Assertions.assertThrows(CustomException.class, () ->
			courseService.enrollStudent(insertedCourse.getId(), studentId));

		Assertions.assertEquals(
			String.format("The student with id %s was not found", studentId),
			exception.getMessage());
	}

	@Test
	public void testInvalidCourseEnrollment() {
		Student student = podamFactory.manufacturePojo(Student.class);
		Student insertedStudent = studentService.saveStudent(student);

		UUID courseId = UUID.randomUUID();
		Exception exception = Assertions.assertThrows(CustomException.class, () ->
				courseService.enrollStudent(courseId, insertedStudent.getId()));

		Assertions.assertEquals(
				String.format("The course with id %s was not found", courseId),
				exception.getMessage());
	}

}
