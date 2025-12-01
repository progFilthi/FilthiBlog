package com.progfilthi.filthiblog.controllers;

import com.progfilthi.filthiblog.models.dto.comment.CommentResponseDto;
import com.progfilthi.filthiblog.models.dto.comment.CreateCommentDto;
import com.progfilthi.filthiblog.models.dto.comment.UpdateCommentDto;
import com.progfilthi.filthiblog.services.comments.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CreateCommentDto dto){
        CommentResponseDto response = commentService.createComment(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<CommentResponseDto> getAllComments(@PageableDefault(size = 10) Pageable pageable){
        return commentService.getAllComments(pageable);
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long comment_id, @Valid
    @RequestBody UpdateCommentDto dto){
        CommentResponseDto response = commentService.updateComment(comment_id, dto);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id){
        commentService.deleteComment(comment_id);
        return ResponseEntity.noContent().build();
    }


}
