package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.LoginParams;
import io.github.rostikq.gamehub.dto.RegisterParams;
import io.github.rostikq.gamehub.dto.Role;
import io.github.rostikq.gamehub.entities.User;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameExistsException;
import io.github.rostikq.gamehub.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    JwtService jwtService;

    @InjectMocks
    AuthService authService;

    @Test
    void registerBlankUsernamePasswordTest(){
        RegisterParams params = new RegisterParams("", "");

        Assertions.assertThrows(IncorrectArgs.class, () -> {
            authService.register(params);
        });
    }

    @Test
    void registerEmptyParamsTest(){
        Assertions.assertThrows(IncorrectArgs.class, () -> {
            authService.register(null);
        });
    }

    @Test
    void registerUsernameExistsTest(){
        String username = "Bob123";
        RegisterParams params = new RegisterParams(username, "123");

        Mockito.when(userRepository.existsByUsername(username)).thenReturn(true);

        Assertions.assertThrows(UsernameExistsException.class, () -> {
            authService.register(params);
        });
    }

    @Test
    void registerTest(){
        RegisterParams params = new RegisterParams("Bob123", "44444444");

        Mockito.when(userRepository.existsByUsername(params.username())).thenReturn(false);

        Mockito.when(passwordEncoder.encode(params.password())).thenReturn("hashed-password");

        Assertions.assertDoesNotThrow(() -> {
            authService.register(params);
        });

        Mockito.verify(userRepository).save(Mockito.argThat(user ->
                user.getUsername().equals(params.username()) &&
                user.getPassword().equals("hashed-password")));
    }

    @Test
    void userNotFoundTest(){
        LoginParams loginParams = new LoginParams("Logan", "4444444");

        Mockito.when(userRepository.findByUsername(loginParams.username())).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            authService.login(loginParams);
        });
    }

    @Test
    void loginIncorrectPasswordTest(){
        LoginParams params = new LoginParams("Logan", "44444444");
        User user = new User();
        user.setUsername(params.username());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode("44444443"));
        user.setRegistrationDate(LocalDateTime.now());

        Mockito.when(userRepository.findByUsername(params.username())).thenReturn(Optional.of(user));

        Assertions.assertThrows(RuntimeException.class, () -> {
            authService.login(params);
        });
    }

    @Test
    void loginValidTest(){
        LoginParams params = new LoginParams("Logan", "44444444");
        User user = new User();
        user.setUsername(params.username());
        user.setRole(Role.USER);
        user.setPassword("encrypted-password");
        user.setRegistrationDate(LocalDateTime.now());

        Mockito.when(userRepository.findByUsername(params.username())).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches(params.password(), user.getPassword())).thenReturn(true);

        Assertions.assertDoesNotThrow(() -> {
            authService.login(params);
        });
    }
}
