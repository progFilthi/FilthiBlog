package com.progfilthi.filthiblog.controllers;

import com.progfilthi.filthiblog.mappers.IPostMapper;
import com.progfilthi.filthiblog.models.dto.post.CreatePostDto;
import com.progfilthi.filthiblog.models.dto.post.PostResponseDto;
import com.progfilthi.filthiblog.services.posts.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody CreatePostDto dto){
        PostResponseDto response = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public Page<PostResponseDto> getAllPosts(@PageableDefault(size = 5) Pageable pageable){
        return postService.getAllPosts(pageable);
    }

    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long post_id){
        PostResponseDto response = postService.getPost(post_id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long post_id,
                                                      @Valid @RequestBody CreatePostDto dto){
        PostResponseDto response = postService.updatePost(dto, post_id);
        return ResponseEntity.ok().body(response);
    }



    @DeleteMapping("/{post_id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long post_id){
        postService.deletePost(post_id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{post_id}/published")
    public ResponseEntity<PostResponseDto> publishPost(@PathVariable Long post_id){
        PostResponseDto response = postService.publishPost(post_id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/drafts")
    public Page<PostResponseDto> getPostDrafts(@PageableDefault(size = 5)
                                                   Pageable pageable){
        return postService.getPostDrafts(pageable);
    }









}
