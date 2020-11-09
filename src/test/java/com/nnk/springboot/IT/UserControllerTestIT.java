package com.nnk.springboot.IT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.fasterxml.jackson.databind.node.TextNode;
import com.nnk.springboot.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    private User user;

    // Constants of test

    String username = "usernameTest";
    String password = "Password1@";
    String fullname = "fullnameTest";
    String role = "USER";

    @Test
    void listUser() throws Exception{

    }

    @Test
    void addUser() throws Exception{

        ObjectMapper obm = new ObjectMapper();
        ObjectNode jsonUser = obm.createObjectNode();

        //GIVEN
        jsonUser.set("username", TextNode.valueOf(username));
        jsonUser.set("password", TextNode.valueOf(password));
        jsonUser.set("fullname", TextNode.valueOf(fullname));
        jsonUser.set("role", TextNode.valueOf(role));

        // WHEN
        // THEN
        mockMvc.perform(MockMvcRequestBuilders.post("/user/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUser.toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"));
    }


}