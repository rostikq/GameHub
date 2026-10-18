package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.auth.JwtAuthenticationFilter;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
public class JwtAuthenticationFilterTest {
    @Mock
    private JwtService jwtService;

    String authenticationToken = "authentication-token";

    private MockHttpServletRequest httpServletRequest;
    private MockHttpServletResponse httpServletResponse;
    private MockFilterChain httpFilterChain;

    @InjectMocks
    private JwtAuthenticationFilter filter;

    @BeforeEach
    void setupBeforeEach(){
        httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.addHeader("Authorization", "Bearer " + authenticationToken);
        httpServletResponse = new MockHttpServletResponse();
        httpFilterChain = new MockFilterChain();
    }

    @Test
    void doFilterSuccess(){
        Mockito.when(jwtService.extractName(authenticationToken)).thenReturn("Bob123");
        Mockito.when(jwtService.extractRole(authenticationToken)).thenReturn("USER");

        Assertions.assertDoesNotThrow(() -> {
            filter.doFilter(httpServletRequest, httpServletResponse, httpFilterChain);
        });

        Assertions.assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterNoAuthHeader(){
        httpServletRequest.removeHeader("Authorization");

        Assertions.assertDoesNotThrow(() -> {
            filter.doFilter(httpServletRequest, httpServletResponse, httpFilterChain);
        });

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInvalidToken(){
        String invalidToken = "invalid-token";

        Mockito.when(jwtService.extractName(invalidToken)).thenThrow(JwtException.class);

        httpServletRequest.removeHeader("Authorization");
        httpServletRequest.addHeader("Authorization", "Bearer " + invalidToken);

        Assertions.assertDoesNotThrow(() -> {
            filter.doFilter(httpServletRequest, httpServletResponse, httpFilterChain);
        });

        Assertions.assertNull(SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext();
    }
}
