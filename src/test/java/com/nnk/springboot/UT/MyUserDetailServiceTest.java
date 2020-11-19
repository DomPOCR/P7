package com.nnk.springboot.UT;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.MyUserDetailService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class MyUserDetailServiceTest {

   @TestConfiguration
   static class MyUserDetailServiceTestsContextConfiguration{


       @Bean
       public MyUserDetailService myUserDetailService(){

           return new MyUserDetailService();
       }
   }
   /*@Test*/
    void loadUserByUsername() {

        // Données de test

     /* String username = "usernameTest";
        String password = "Password1@";
        String fullname = "fullnameTest";
        String role = "USER";

        //GIVEN
        User userTest = new User(username,password,fullname,role);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(userTest);

        GrantedAuthority authority = new SimpleGrantedAuthority(userTest.getRole());
        UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(userTest.getUsername(),
                userTest.getPassword(), Arrays.asList(authority));

        Mockito.when(myUserDetailService.loadUserByUsername(username)).thenReturn(userDetails);*/

    }
}