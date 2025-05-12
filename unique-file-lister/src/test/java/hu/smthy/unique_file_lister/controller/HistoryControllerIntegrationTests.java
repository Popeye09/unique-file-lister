package hu.smthy.unique_file_lister.controller;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class HistoryControllerIntegrationTests {
    private final MockMvc mockMvc;

    @Autowired
    public HistoryControllerIntegrationTests(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void testHistoryReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/history")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testHistoryReturnsCorrectData() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/getUnique/bin?username="+System.getProperty("user.name"))
                        .contentType(MediaType.APPLICATION_JSON)
        );
        mockMvc.perform(
                MockMvcRequestBuilders.get("/history")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].directory").value("/bin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].timestamp").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value(System.getProperty("user.name")));
    }
}
