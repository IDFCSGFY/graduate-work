package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.impl.CommentService;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/ads")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/{id}/comments")
    public ResponseEntity<Comments> findAllCommentsByAdId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findAllByAdId(id));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Comment> createComment(Authentication authentication, @PathVariable Integer id, @RequestBody CreateOrUpdateComment updateComment) {
        return ResponseEntity.ok(service.createComment(authentication, id, updateComment));
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(Authentication authentication, @PathVariable Integer adId, @PathVariable Integer commentId) {
        service.deleteCommentById(authentication, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment(Authentication authentication, @PathVariable Integer adId, @PathVariable Integer commentId, @RequestBody CreateOrUpdateComment updateComment) {
        return ResponseEntity.ok(service.updateCommentById(authentication, commentId, updateComment));
    }
}
