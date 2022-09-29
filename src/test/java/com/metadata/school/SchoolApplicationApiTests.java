package com.metadata.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jayway.jsonpath.JsonPath;
import com.metadata.school.school.course.Course;
import com.metadata.school.school.course.CourseDTO;
import com.metadata.school.school.student.StudentDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mapping.SimplePropertyHandler;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class SchoolApplicationApiTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testCourseApiGetCoursesWithoutStudents() throws Exception {
        mvc.perform(MockMvcRequestBuilders
            .get("/course/without-student")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testCourseApiSaveNewCourse() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testCourseApiUpdateCourse() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .put("/course/"+id)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testCourseApiDeleteCourse() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .delete("/course/"+id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testCourseApiSaveNewCourseInvalidNameSize() throws Exception {

        String payload = new CoursePayloadBuilder().setName("a").build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest()).andReturn();

        String errorMessage = JsonPath.read(result.getResponse().getContentAsString(), "$.name");
        Assertions.assertEquals("The student name must have at least 3 and max 255 characters", errorMessage);
    }

    @Test
    public void testCourseApiSaveNewCourseInvalidDate() throws Exception {

        String payload = new CoursePayloadBuilder().setStartDate(null).build();

        mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCourseApiSaveNewCourseInvalidDescription() throws Exception {

        String payload = new CoursePayloadBuilder().setDescription("a").build();

        mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testStudentApiSaveNewStudent() throws Exception {

        String payload = new StudentPayloadBuilder().build();

        mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testStudentApiUpdateStudent() throws Exception {

        String payload = new StudentPayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .put("/student/"+id)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testStduentApiDeleteStudent() throws Exception {

        String payload = new StudentPayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String id = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .delete("/student/"+id)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());
    }

    @Test
    public void testCourseApiEnrollStudent() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String courseId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        String payloadStudent = new StudentPayloadBuilder().build();

        MvcResult resultStudent = mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payloadStudent)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String studentId = JsonPath.read(resultStudent.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .post("/course/"+courseId+"/enroll-student/"+studentId)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testCourseApiEnrollStudentDuplicate() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String courseId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        String payloadStudent = new StudentPayloadBuilder().build();

        MvcResult resultStudent = mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payloadStudent)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String studentId = JsonPath.read(resultStudent.getResponse().getContentAsString(), "$.id");

        mvc.perform(MockMvcRequestBuilders
            .post("/course/"+courseId+"/enroll-student/"+studentId)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());

        MvcResult resultEnrollError = mvc.perform(MockMvcRequestBuilders
            .post("/course/"+courseId+"/enroll-student/"+studentId)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest()).andReturn();

        String errorMessage = JsonPath.read(resultEnrollError.getResponse().getContentAsString(), "$.error");
        Assertions.assertTrue(errorMessage.contains("is already enrolled on course"));
    }

    @Test
    public void testCourseApiEnrollInvalidStudent() throws Exception {

        String payload = new CoursePayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String courseId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        UUID uuid = UUID.randomUUID();
        MvcResult resultEnrollError = mvc.perform(MockMvcRequestBuilders
            .post("/course/"+courseId+"/enroll-student/" + uuid)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest()).andReturn();

        String errorMessage = JsonPath.read(resultEnrollError.getResponse().getContentAsString(), "$.error");
        Assertions.assertTrue(errorMessage.contains("The student with id "+uuid+" was not found"));
    }

    @Test
    public void testCourseApiEnrollInvalidCourse() throws Exception {

        String payload = new StudentPayloadBuilder().build();

        MvcResult result = mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated()).andReturn();

        String studentId = JsonPath.read(result.getResponse().getContentAsString(), "$.id");

        UUID uuid = UUID.randomUUID();
        MvcResult resultEnrollError = mvc.perform(MockMvcRequestBuilders
            .post("/course/" + uuid + "/enroll-student/" + studentId)
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest()).andReturn();

        String errorMessage = JsonPath.read(resultEnrollError.getResponse().getContentAsString(), "$.error");
        Assertions.assertTrue(errorMessage.contains("The course with id "+uuid+" was not found"));
    }

    private class CoursePayloadBuilder {

        private final CourseDTO course;
        private final ObjectMapper mapper = new ObjectMapper();

        public CoursePayloadBuilder() {
            course = new CourseDTO();
            course.setName("fugit suscipit in");
            course.setDescription("Vero qui quas illum aut doloremque ipsum nemo veniam fugiat. Ut labore aut explicabo consectetur. Nisi accusantium et et ut et.");
            course.setStartDate(new Date());
            course.setEndDate(new Date());
        }

        public String build() {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(course);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        public CoursePayloadBuilder setName(String name){
            course.setName(name);
            return this;
        }

        public CoursePayloadBuilder setDescription(String description){
            course.setDescription(description);
            return this;
        }

        public CoursePayloadBuilder setStartDate(Date startDate){
            course.setStartDate(startDate);
            return this;
        }

        public CoursePayloadBuilder setEndDate(Date endDate){
            course.setEndDate(endDate);
            return this;
        }

    }

    private class StudentPayloadBuilder {

        private final StudentDTO student;
        private final ObjectMapper mapper = new ObjectMapper();

        public StudentPayloadBuilder() {
            student = new StudentDTO();
            student.setName("Erick Heaney");
            student.setDocument("1234567");
            student.setBirthDate(new Date());
            student.setPhoneNumber("86-709-625-6486");
        }

        public String build() {
            try {
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(student);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        public StudentPayloadBuilder setName(String name){
            student.setName(name);
            return this;
        }

        public StudentPayloadBuilder setDocument(String document){
            student.setDocument(document);
            return this;
        }

        public StudentPayloadBuilder setBirthDate(Date birthDate){
            student.setBirthDate(birthDate);
            return this;
        }

        public StudentPayloadBuilder setPhoneNumber(String phoneNumber){
            student.setPhoneNumber(phoneNumber);
            return this;
        }

    }

}
