package io.github.rostikq.gamehub.repositories;

import io.github.rostikq.gamehub.dto.ReviewRequestDto;
import io.github.rostikq.gamehub.entities.Review;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("""
            select new io.github.rostikq.gamehub.dto.ReviewRequestDto
            (r.id, r.title, r.description, u.username, g.name, g.id, r.publicationTime, r.rate)
            from Review r
            JOIN r.game g 
            JOIN r.author u
            WHERE g.id = :gameId 
            ORDER BY r.publicationTime DESC
                        """)
    List<ReviewRequestDto> findRecentByGameId(Long gameId, Limit limit);

    @Query("""
        SELECT new io.github.rostikq.gamehub.dto.ReviewRequestDto
        (r.id, r.title, r.description, u.username, g.name, g.id, r.publicationTime, r.rate)
        from Review r
        JOIN r.game g
        JOIN r.author u
        WHERE u.id = :userId
        ORDER BY r.publicationTime DESC
        """)
    List<ReviewRequestDto> findByUserId(Long userId);

    boolean existsByAuthorIdAndGameId(Long userId, Long gameId);
}
