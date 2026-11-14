package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.CommentRequest;
import io.github.rostikq.gamehub.entities.Comment;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameEmptyException;
import io.github.rostikq.gamehub.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    final CommentService service;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody CommentRequest request){
        try {
            logger.info("Submitting comment from {}", request.username());
            service.submit(request);
            return ResponseEntity.ok("Submit is successfull");
        } catch (IncorrectArgs | UsernameEmptyException e) {
            logger.error("Incorrect arguments for submit comment from {}\n{}", request.username(), e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getlast")
    public ResponseEntity<List<Comment>> getLast(){
        return ResponseEntity.ok(service.getLast());
    }
}
