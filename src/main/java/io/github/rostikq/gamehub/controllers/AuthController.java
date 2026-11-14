package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.AuthResponse;
import io.github.rostikq.gamehub.dto.LoginParams;
import io.github.rostikq.gamehub.dto.RegisterParams;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameExistsException;
import io.github.rostikq.gamehub.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService _service;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService service) {
        _service = service;
    }

    @PostMapping("/register")
    public String register(@RequestBody RegisterParams params){
        logger.debug("Received register request for {}", params.username());
        String responseMessage;
        try {
            _service.register(params);

            responseMessage = params.username() + " has registered successfully";
            logger.info("Received register request for {} was successful", params.username());
        } catch (UsernameExistsException e) {
            responseMessage = "User with username " + params.username() + " already exists";
            logger.warn(responseMessage);
        } catch (IncorrectArgs e) {
            responseMessage = "Wrong registration args";
            logger.warn(responseMessage);
        }

        return responseMessage;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginParams params){
        return
                _service.login(params);
    }



}
