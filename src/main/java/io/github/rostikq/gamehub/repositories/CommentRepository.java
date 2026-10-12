package io.github.rostikq.gamehub.repositories;

import io.github.rostikq.gamehub.entities.Comment;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c ORDER BY c.publicationDateTime DESC")
    public List<Comment> findOrderByPublicationDateTimeDesc(Limit limit);
}
