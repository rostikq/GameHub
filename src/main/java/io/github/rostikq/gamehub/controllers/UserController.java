package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.services.GameService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    public final GameService _service;

    public UserController(GameService service) {
        _service = service;
    }


}
