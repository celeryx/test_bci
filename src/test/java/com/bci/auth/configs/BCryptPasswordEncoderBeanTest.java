package com.bci.auth.configs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BCryptPasswordEncoderBeanTest {

    @Autowired
    private ApplicationContext context;

    @Test
    public void bCryptPasswordEncoderBeanExists() {
        assertTrue(context.containsBean("bCryptPasswordEncoder"));
    }

    @Test
    public void bCryptPasswordEncoderBeanIsCorrectType() {
        assertTrue(context.getBean("bCryptPasswordEncoder") instanceof BCryptPasswordEncoder);
    }

    @Test
    public void bCryptPasswordEncoderBeanWorksAsExpected() {
        BCryptPasswordEncoder encoder = context.getBean(BCryptPasswordEncoder.class);
        String rawPassword = "password";
        String encodedPassword = encoder.encode(rawPassword);

        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}
