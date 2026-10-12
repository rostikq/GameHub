package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.AuthResponse;
import io.github.rostikq.gamehub.dto.LoginParams;
import io.github.rostikq.gamehub.dto.RegisterParams;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameExistsException;
import io.github.rostikq.gamehub.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService _service;

    public AuthController(AuthService service) {
        _service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterParams params){
        String responseMessage;
        try {
            _service.register(params);

            responseMessage = params.username() + " has registered successfully";
        } catch (UsernameExistsException e) {
            responseMessage = "User with username " + params.username() + " already exists";
        } catch (IncorrectArgs e) {
            responseMessage = "Wrong registration args";
        }

        return responseMessage;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginParams params){
        return _service.login(params);
    }



}
