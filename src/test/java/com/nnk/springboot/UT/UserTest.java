package com.nnk.springboot.UT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    String username = "usernameTest";
    String password = "Password1@";
    String badPassword = "password1@";
    String fullname = "fullnameTest";
    String role = "USER";

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Show the list of user */
    @Test
    void listUser() throws Exception{

        List<User> userList = new ArrayList<>();

        //GIVEN : Give an exiting Person
        User userTest = new User(username,password,fullname,role);
        userList.add(userTest);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //WHEN //THEN return the list page
        mockMvc.perform(get("/user/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/list"));
    }

    /* Display user adding form*/
    @Test
    void addUser() throws Exception{

        //GIVEN

        //WHEN //THEN return the add page
        mockMvc.perform(get("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/add"));
    }

    /* Validate correct user */
    @Test
    void validateUser_CorrectUser() throws Exception{

        //GIVEN : Give a new user
        User userTest = new User(username,password,fullname,role);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(userTest);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(userTest));

        //WHEN //THEN return the list page
        mockMvc.perform(post("/user/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("fullname", fullname)
                .param("username", username)
                .param("password", password)
                .param("role",role))
                .andDo(print())
                .andExpect(view().name("redirect:/user/list"))
                .andExpect(status().is3xxRedirection());
    }

    /* Display user updating form */
    @Test
    void updateUser_UserIsReturn() throws Exception{

        //GIVEN : Give an exiting user
        User userTest = new User(username,password,fullname,role);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(userTest));

        //WHEN //THEN return the update page
        mockMvc.perform(get("/user/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/update"));
    }

    /* Display delete a user */
    @Test
    void DeleteUser_UserListIsReturn() throws Exception{

        //GIVEN : Give an exiting Person
        User userTest = new User(username,password,fullname,role);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(Optional.of(userTest));

        //WHEN //THEN return the list page
        mockMvc.perform(get("/user/delete/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/user/list"));

        Mockito.verify(userRepository,Mockito.times(1)).delete(any(User.class));
    }

}
