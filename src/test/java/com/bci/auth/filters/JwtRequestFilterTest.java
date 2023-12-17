package com.bci.auth.filters;

import com.bci.auth.commons.filters.JwtRequestFilter;
import com.bci.auth.services.JwtTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtRequestFilterTest {

    private static class TestableJwtRequestFilter extends JwtRequestFilter {
        public TestableJwtRequestFilter(JwtTokenService jwtTokenService) {
            super(jwtTokenService);
        }

        public void testDoFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                throws ServletException, IOException {
            super.doFilterInternal(request, response, chain);
        }
    }

    @Mock
    private JwtTokenService jwtTokenService;

    private TestableJwtRequestFilter jwtRequestFilter;

    @Mock
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        jwtRequestFilter = new TestableJwtRequestFilter(jwtTokenService);
        SecurityContextHolder.setContext(new SecurityContextImpl());
    }

    @Test
    void doFilterInternal_ValidToken_SetsAuthentication() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer fakeToken");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtTokenService.validateToken("fakeToken")).thenReturn(true);
        when(jwtTokenService.extractUsername("fakeToken")).thenReturn("user");

        jwtRequestFilter.testDoFilterInternal(request, response, filterChain);

        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals("user", SecurityContextHolder.getContext().getAuthentication().getName());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilterInternal_InvalidToken_DoesNotSetAuthentication() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer invalidToken");
        MockHttpServletResponse response = new MockHttpServletResponse();

        when(jwtTokenService.validateToken("invalidToken")).thenReturn(false);

        jwtRequestFilter.testDoFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());

        verify(filterChain, times(1)).doFilter(request, response);
    }

    // Additional test cases as needed
}
