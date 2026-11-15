package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.GameDto;
import io.github.rostikq.gamehub.dto.ReviewSubmitDto;
import io.github.rostikq.gamehub.entities.Game;
import io.github.rostikq.gamehub.entities.Review;
import io.github.rostikq.gamehub.entities.User;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.mapper.GameMapper;
import io.github.rostikq.gamehub.repositories.ReviewRepository;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private UserService userService;
    @Mock
    private GameService gameService;
    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private ReviewService reviewService;

    ReviewSubmitDto validSubmitDto = new ReviewSubmitDto("Review Title",
            "Review Description",
            BigDecimal.valueOf(9.9),
            0);

    ReviewSubmitDto invalidSubmitDto = new ReviewSubmitDto("Review Title",
            "",
            BigDecimal.valueOf(9.9),
            0);

    GameDto connectedGameDto = new GameDto(5, "Game", "Desc", 2000, "Genre", "");


    @Test
    public void submitReviewSuccess() {

        String authorToken = "Bearer author-token";

        Mockito.when(gameService.exists(validSubmitDto.gameId())).thenReturn(true);
        Mockito.when(userService.retrieveUserFromToken("author-token")).thenReturn(new User());
        Mockito.when(gameService.get(validSubmitDto.gameId())).thenReturn(connectedGameDto);
        Mockito.when(gameMapper.dtoToEntity(connectedGameDto)).thenReturn(new Game());


        Assertions.assertDoesNotThrow(() -> {
            reviewService.submit(validSubmitDto, authorToken);
        });
    }

    @Test
    public void submitReviewGameNotFound() {
        String authorToken = "Bearer author-token";

        Mockito.when(userService.retrieveUserFromToken("author-token")).thenReturn(new User());
        Mockito.when(gameService.exists(validSubmitDto.gameId())).thenReturn(false);

        Assertions.assertThrows(IncorrectArgs.class,() -> {
            reviewService.submit(validSubmitDto, authorToken);
        });
    }

    @Test
    public void submitReviewAuthorTokenInvalid() {
        String authorToken = "Bearer author-token";

        Mockito.when(userService.retrieveUserFromToken("author-token")).thenThrow(JwtException.class);


        Assertions.assertThrows(IncorrectArgs.class,() -> {
            reviewService.submit(validSubmitDto, authorToken);
        });
    }

    @Test
    public void submitReviewInvalidDto() {
        String authorToken = "Bearer author-token";

        Mockito.when(userService.retrieveUserFromToken("author-token")).thenThrow(JwtException.class);


        Assertions.assertThrows(IncorrectArgs.class,() -> {
            reviewService.submit(validSubmitDto, authorToken);
        });
    }
}
