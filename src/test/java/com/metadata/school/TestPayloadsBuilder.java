package com.metadata.school;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metadata.school.school.course.CourseDTO;
import com.metadata.school.school.student.StudentDTO;

import java.util.Date;
class CoursePayloadBuilder {

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

class StudentPayloadBuilder {

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


