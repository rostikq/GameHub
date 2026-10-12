package io.github.rostikq.gamehub.services;

import io.github.rostikq.gamehub.dto.CommentRequest;
import io.github.rostikq.gamehub.entities.Comment;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameEmptyException;
import io.github.rostikq.gamehub.repositories.CommentRepository;
import org.hibernate.query.spi.Limit;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public void submit(CommentRequest request) throws UsernameEmptyException, IncorrectArgs {
        if (request == null) throw new IncorrectArgs("Comment Request is empty");

        if (request.username().isEmpty()) throw new UsernameEmptyException("Username is empty");

        Comment comment = new Comment(request.username(), request.comment(), LocalDateTime.now());

        repository.save(comment);
    }

    public List<Comment> getLast(){
        return repository.findOrderByPublicationDateTimeDesc(new Limit(0, 10));
    }
}
