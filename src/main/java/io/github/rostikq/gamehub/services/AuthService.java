package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.AuthResponse;
import io.github.rostikq.gamehub.dto.LoginParams;
import io.github.rostikq.gamehub.dto.RegisterParams;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameExistsException;
import io.github.rostikq.gamehub.dto.Role;
import io.github.rostikq.gamehub.entities.User;
import io.github.rostikq.gamehub.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {
    private final UserRepository _repository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtService _jwtService;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        _repository = repository;
        _passwordEncoder = passwordEncoder;
        _jwtService = jwtService;
    }

    public void register(RegisterParams params) throws IncorrectArgs {
        if (params == null) throw new IncorrectArgs("Wrong registration params!");


        if (_repository.existsByUsername(params.username())){
            throw new UsernameExistsException("This username exists");
        }

        User user = new User();

        user.setUsername(params.username());
        user.setPassword(_passwordEncoder.encode(params.password()));
        user.setRegistrationDate(LocalDateTime.now());

        _repository.save(user);
    }

    public AuthResponse login(LoginParams params){
        User user = _repository.findByUsername(params.username()).orElseThrow(() ->
                new RuntimeException("There's no such user"));

        if (!_passwordEncoder.matches(params.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        if (user.getRole() == null){
            System.out.print("WARNING: " + params.username() + " does not have role. Assuming them to be USER.");
            user.setRole(Role.USER);
        }

        String token = _jwtService.generateToken(user);

        return new AuthResponse(token);
    }

}
