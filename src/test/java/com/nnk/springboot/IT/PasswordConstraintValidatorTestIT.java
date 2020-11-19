package com.nnk.springboot.IT;

import com.nnk.springboot.config.PasswordConstraintValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordConstraintValidatorTestIT {

    private PasswordConstraintValidator validator = new PasswordConstraintValidator();

    private boolean isValid(String password){
        return validator.isValid(password,null);
    }

    @Test
    void isValidPassword_ReturnTrue(){
        assertTrue(isValid("Password1@"));
    }

    @Test
    void isNotValidPassword_ReturnFalse(){
        assertFalse(isValid(""));
        assertFalse(isValid("password"));
        assertFalse(isValid("Password"));
        assertFalse(isValid("Password1"));
        assertFalse(isValid("Pass1@"));

    }
}