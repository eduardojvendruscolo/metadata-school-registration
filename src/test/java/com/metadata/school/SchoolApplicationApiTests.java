package com.metadata.school;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

        String payload = "{\n    \"name\": \"ad et aliquam\",\n    " +
                "\"description\": \"Nostrum qui aliquid enim ipsam ut sed hic vel. Dolor quae adipisci saepe qui " +
                "harum natus et. Est est quasi eum error dolor. Vel voluptas nihil molestias temporibus. " +
                "Sint ullam et.\",\n    \"startDate\": \"1664458976\",\n    \"endDate\": \"1664458976\"\n}";

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

        String payload = "{\n    \"name\": \"animi aut nemo\",\n    \"description\": \"Delectus sequi distinctio. " +
                "Inventore ad doloribus aspernatur laborum. Error aspernatur atque ut. Saepe amet suscipit laborum " +
                "omnis omnis aut veritatis. Hic ut unde quia molestiae fugit neque quam laboriosam " +
                "et.\",\n    \"startDate\": \"1664459632\",\n    \"endDate\": \"1664459632\"\n}";

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

        String payload = "{\n    \"name\": \"voluptatem tenetur omnis\",\n    \"description\": \"Hic voluptatem similique" +
                " et. Sint earum est. Aut qui atque ad inventore magnam quo nobis ipsam. Ea in enim voluptas ab. Nihil" +
                " non molestiae.\",\n    \"startDate\": \"1664459907\",\n    \"endDate\": \"1664459907\"\n}";

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

        String payload = "{\n    \"name\": \"a\",\n    " +
                "\"description\": \"Nostrum qui aliquid enim ipsam ut sed hic vel. Dolor quae adipisci saepe qui " +
                "harum natus et. Est est quasi eum error dolor. Vel voluptas nihil molestias temporibus. " +
                "Sint ullam et.\",\n    \"startDate\": \"1664458976\",\n    \"endDate\": \"1664458976\"\n}";

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

        String payload = "{\n    \"name\": \"a\",\n    " +
                "\"description\": \"Nostrum qui aliquid enim ipsam ut sed hic vel. Dolor quae adipisci saepe qui " +
                "harum natus et. Est est quasi eum error dolor. Vel voluptas nihil molestias temporibus. " +
                "Sint ullam et.\",\n    \"startDate\": \"abc\"}";

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

        String payload = "{\n    \"name\": \"aliquid sed\",\n    " +
                "\"description\": \"ab\",\n    \"startDate\": \"1664458976\",\n    \"endDate\": \"1664458976\"\n}";

        mvc.perform(MockMvcRequestBuilders
            .post("/course")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest());
    }

    @Test
    public void testCourseApiSaveNewStudent() throws Exception {

        String payload = "{\n    \"name\": \"Noel Kautzer\",\n    \"document\": \"229\",\n" +
                "\"birthDate\": 1664462036,\n    \"phoneNumber\": \"42-239-228-9281\"\n}";

        mvc.perform(MockMvcRequestBuilders
            .post("/student")
            .content(payload)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated());
    }

    @Test
    public void testCourseApiUpdateStudent() throws Exception {

        String payload = "{\n    \"name\": \"Phil McKenzie\",\n    \"document\": \"917\",\n    " +
                "\"birthDate\": 1664462481,\n    \"phoneNumber\": \"69-511-698-5522\"\n}";

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
    public void testCourseApiDeleteStudent() throws Exception {

        String payload = "{\n    \"name\": \"Elmer Breitenberg\",\n    \"document\": \"596\",\n    " +
                "\"birthDate\": 1664462452,\n    \"phoneNumber\": \"81-777-835-5590\"\n}";

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

}
