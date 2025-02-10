package org.rjpetersen.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
class MessageServiceTest {

    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    public MessageServiceTest(@Autowired MockMvc mockMvc, @Autowired ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.mapper = objectMapper;
    }

    @Test
    void enqueueAndDequeue() throws Exception {
        mockMvc.perform(get("/v1/dequeue/"))
                .andExpect(status().isNotFound());

        mockMvc.perform(post("/v1/enqueue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new MessageDTO("Test Message 1"))))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/v1/enqueue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new MessageDTO("Test Message 2"))))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/v1/enqueue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(new MessageDTO("Test Message 3"))))
                .andExpect(status().isCreated());
        mockMvc.perform(get("/v1/queue-size"))
                .andExpect(content().string("3"));

        mockMvc.perform(
                    get("/v1/dequeue")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Test Message 1")));

        mockMvc.perform(get("/v1/queue-size"))
                .andExpect(content().string("2"));

    }
}