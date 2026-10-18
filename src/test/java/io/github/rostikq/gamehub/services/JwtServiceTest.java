package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.Role;
import io.github.rostikq.gamehub.entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

public class JwtServiceTest {
    private final JwtService jwtService = new JwtService();

    private String generatedToken;
    private String invalidToken;
    private final User testUser = new User();

    @BeforeEach
    void setup(){
        testUser.setUsername("Bob123");
        testUser.setRole(Role.USER);
        generatedToken = jwtService.generateToken(testUser);

        StringBuilder invalidTokenBuilder = new StringBuilder(generatedToken);
        invalidTokenBuilder.setCharAt(generatedToken.indexOf(".") + 1, '.');
        invalidToken = invalidTokenBuilder.toString();

    }

    @Test
    void extractUsernameSuccessTest(){
        Assertions.assertDoesNotThrow(() -> {
            jwtService.extractName(generatedToken);
        });
    }

    @Test
    void extractUsernameInvalidTest(){
        Assertions.assertThrows(Exception.class, () -> {
            jwtService.extractName(invalidToken);
        });
    }

    @Test
    void extractRoleSuccessTest(){
        Assertions.assertDoesNotThrow(() -> {
            jwtService.extractRole(generatedToken);
        });
    }

    @Test
    void extractRoleInvalidTest(){
        Assertions.assertThrows(Exception.class, () -> {
            jwtService.extractRole(invalidToken);
        });
    }
}
