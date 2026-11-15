package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.ReviewSubmitDto;
import io.github.rostikq.gamehub.dto.ReviewRequestDto;
import io.github.rostikq.gamehub.entities.Review;
import io.github.rostikq.gamehub.entities.User;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.mapper.GameMapper;
import io.github.rostikq.gamehub.repositories.ReviewRepository;
import io.jsonwebtoken.JwtException;
import org.hibernate.query.spi.Limit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final GameService gameService;
    private final GameMapper gameMapper;

    private final Logger logger =  LoggerFactory.getLogger(this.getClass());

    public ReviewService(ReviewRepository reviewRepository, UserService userService, GameService gameService, GameMapper gameMapper) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    public void submit(ReviewSubmitDto reviewRequest, String authorHeader) throws IncorrectArgs {
        String token = authorHeader.substring(7);

        User author;
        try {
            author = userService.retrieveUserFromToken(token);
        } catch (JwtException e) {
            logger.warn("Could not retrieve author by token while submitting review:\n{}", e.getMessage());
            throw new IncorrectArgs("Could not retrieve author by token");
        }

        if (!gameService.exists(reviewRequest.gameId())) {
            logger.warn("Could not find game while submitting review: {}", reviewRequest.gameId());
            throw new IncorrectArgs("Could not find game by id: " + reviewRequest.gameId());
        }

        if (reviewRepository.existsByAuthorIdAndGameId(author.getId(), reviewRequest.gameId())) {
            logger.warn("Review already exists by author id and game id {}", reviewRequest.gameId());
            throw new IncorrectArgs("Review submitted by this author to this game already exists");
        }

        if (reviewRequest.title() != null && !reviewRequest.title().isBlank()){
            if (reviewRequest.description() == null || reviewRequest.description().isBlank()){
                logger.warn("Title exists but description is blank.");
                throw new IncorrectArgs("Title exists but description is blank.");
            }
        }

        Review review = new Review();
        review.setAuthor(author);
        review.setGame(gameMapper.dtoToEntity(gameService.get(reviewRequest.gameId())));
        review.setTitle(reviewRequest.title());
        review.setDescription(reviewRequest.description());
        review.setRate(reviewRequest.rating());
        review.setPublicationTime(ZonedDateTime.now());
        reviewRepository.save(review);
        logger.info("Saved review successfully: {}", review.getId());
    }

    public List<ReviewRequestDto> getRecentGameReviews(long gameId, int count) throws IncorrectArgs {
        if (!gameService.exists(gameId)){
            logger.warn("Could not find game while getting recent reviews: {}", gameId);
            throw new IncorrectArgs("Could not find game: " + gameId);
        }

        return reviewRepository.findRecentByGameId(gameId, new Limit(0, count));
    }

    public List<ReviewRequestDto> getUserReviews(long userId) throws IncorrectArgs {
        if (!userService.existsById(userId)){
            logger.warn("Could not find user by id while getting user reviews: {}", userId);
            throw new IncorrectArgs("Could not find user: " + userId);
        }

        return reviewRepository.findByUserId(userId);
    }
}
