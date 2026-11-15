package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.entities.User;
import io.github.rostikq.gamehub.repositories.UserRepository;
import io.jsonwebtoken.JwtException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User retrieveUserFromToken(String token) throws JwtException {
        String username = jwtService.extractName(token);
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean existsById(Long id){
        return userRepository.existsById(id);
    }
}
