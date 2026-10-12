package io.github.rostikq.gamehub.controllers;

import io.github.rostikq.gamehub.dto.CommentRequest;
import io.github.rostikq.gamehub.entities.Comment;
import io.github.rostikq.gamehub.exceptions.IncorrectArgs;
import io.github.rostikq.gamehub.exceptions.UsernameEmptyException;
import io.github.rostikq.gamehub.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submit(@RequestBody CommentRequest request){
        try {
            service.submit(request);
            System.out.println("Comment was submitted by " + request.username());
            return ResponseEntity.ok("Submit is successfull");
        } catch (IncorrectArgs | UsernameEmptyException e) {
            System.out.println("Exception while submitting comment: " + e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getlast")
    public ResponseEntity<List<Comment>> getLast(){
        return ResponseEntity.ok(service.getLast());
    }
}
