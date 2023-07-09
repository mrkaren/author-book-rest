package com.example.authorbookrest.endpoint;

import com.example.authorbookrest.dto.CreateAuthorRequestDto;
import com.example.authorbookrest.entity.Author;
import com.example.authorbookrest.entity.Role;
import com.example.authorbookrest.entity.User;
import com.example.authorbookrest.repository.AuthorRepository;
import com.example.authorbookrest.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthorEndpointTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    public void after(){
        userRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    @WithMockUser("user@company.com")
    void create() throws Exception {
        createTestUser();
        CreateAuthorRequestDto createAuthorRequestDto = CreateAuthorRequestDto.builder()
                .email("author1@mail.com")
                .name("Poxos")
                .surname("poxosyan")
                .build();
        String jsonAuthor = new ObjectMapper().writeValueAsString(createAuthorRequestDto);
        mvc.perform(post("/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonAuthor)
                ).andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("author1@mail.com"));
        Optional<Author> byEmail = authorRepository.findByEmail("author1@mail.com");
        assertTrue(byEmail.isPresent());
    }

    @Test
    @WithMockUser("user@company.com")
    public void testDelete_OK() throws Exception {
        createTestUser();
        Author author = authorRepository.save(Author.builder()
                .email("author@mail.com")
                .name("poxos")
                .surname("poxosyan")
                .build());
        Optional<Author> byEmail = authorRepository.findByEmail(author.getEmail());
        assertTrue(byEmail.isPresent());
        mvc.perform(delete("/author/"+ author.getId()))
                .andExpect(status().is(204));
        assertTrue(authorRepository.findByEmail(author.getEmail()).isEmpty());

    }

    private void createTestUser() {
        userRepository.save(User.builder()
                .id(1)
                .email("user@company.com")
                .name("poxos")
                .surname("poxosyan")
                .password("poxos")
                .role(Role.USER)
                .build());
    }
}
