package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.GameDto;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.mapper.GameMapper;
import io.github.rostikq.gamehub.repositories.GameRepository;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class GameServiceTest {

    @Mock
    private GameRepository repository;

    @Mock
    private GameMapper mapper;

    @InjectMocks
    private GameService service;


    @Test
    void addIncorrectArgsExceptionTest(){
        GameDto request = new GameDto("", "", 2000, "", "");
        Assertions.assertThrows(IncorrectArgs.class, () -> {
            service.add(request);
        });
    }

    @Test
    void getNoSuchElementExceptionTest(){
        Mockito.when(repository.findById(0L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            service.get(0L);
        });
    }
}
