package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

@RestController
public class CommentController {

    @GetMapping("ads/{id}/comments")
    public ResponseEntity<Comments> findAllCommentsByAdId(@PathVariable Integer id) {
        return ResponseEntity.ok(new Comments());
    }

    @PostMapping("ads/{id}/comments")
    public ResponseEntity<Comment> createComment(@PathVariable Integer id, @RequestBody CreateOrUpdateComment updateComment) {
        return ResponseEntity.ok(new Comment());
    }

    @DeleteMapping("ads/{adId}/comments/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.ok().build();
    }

    @PatchMapping("ads/{adId}/comments/{commentId}")
    public ResponseEntity<Comment> updateComment (@PathVariable Integer adId, @PathVariable Integer commentId) {
        return ResponseEntity.ok(new Comment());
    }
}
