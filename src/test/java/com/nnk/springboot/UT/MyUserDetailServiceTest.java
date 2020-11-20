package com.nnk.springboot.UT;

import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.MyUserDetailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(SpringExtension.class)
public class MyUserDetailServiceTest {

    @MockBean
    UserRepository userRepository;

    @Autowired
    MyUserDetailService myUserDetailService;

    @TestConfiguration
    static class MyUserDetailServiceTestsContextConfiguration {


        @Bean
        public MyUserDetailService myUserDetailService() {

            return new MyUserDetailService();
        }
    }

    @Test
    void loadUserByUsernameIsEmpty() {


        //GIVEN
        String username = "";

        //WHEN
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        //THEN return Exception
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailService.loadUserByUsername(username));
    }

    @Test
    void loadUserByUsernameNotFound() {


        //GIVEN
        String username = "username9999";

        //WHEN
        Mockito.when(userRepository.findByUsername(username)).thenReturn(null);

        //THEN return Exception
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailService.loadUserByUsername(username));
    }
}