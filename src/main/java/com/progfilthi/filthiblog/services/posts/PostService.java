package com.progfilthi.filthiblog.services.posts;

import com.progfilthi.filthiblog.enums.PostStatus;
import com.progfilthi.filthiblog.enums.Roles;
import com.progfilthi.filthiblog.globalExceptionHandler.AccessDeniedException;
import com.progfilthi.filthiblog.globalExceptionHandler.ResourceNotFoundException;
import com.progfilthi.filthiblog.mappers.IPostMapper;
import com.progfilthi.filthiblog.models.Post;
import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.models.dto.post.CreatePostDto;
import com.progfilthi.filthiblog.models.dto.post.PostResponseDto;
import com.progfilthi.filthiblog.repositories.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final IPostRepository postRepository;
    private final IPostMapper postMapper;

    @Transactional
    public PostResponseDto createPost(CreatePostDto dto){
        //Converting Dto to entity
        Post post = postMapper.toPostEntity(dto);

        //Getting the logged-in user
        User currentUser = getCurrentUser();

        //Assigning the logged-in user to the post
        post.setUser(currentUser);

        //Saving post to the database
        Post savedPost = postRepository.save(post);

        //Returning the savedPost to our response / client
        return postMapper.toPostResponseDto(savedPost);
    }

    @Transactional(readOnly = true)
    public Page<PostResponseDto> getAllPosts(Pageable pageable){
        return postRepository.findByStatusOrderByCreatedAtDesc(PostStatus.PUBLISHED, pageable)
                .map(postMapper::toPostResponseDto);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post with id " + id + " not found")
        );

        return postMapper.toPostResponseDto(post);
    }


    @Transactional
    public PostResponseDto updatePost(CreatePostDto dto, Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post with id " + id + " not found")
        );

        checkOwnershipOrAdmin(post);

        postMapper.updateFromDto(dto, post);

        return postMapper.toPostResponseDto(postRepository.save(post));
    }

    @Transactional
    public void deletePost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post with id " + id + " not found")
        );

        checkOwnershipOrAdmin(post);

        postRepository.deleteById(id);
    }

    @Transactional
    public PostResponseDto publishPost(Long id){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Post with id " + id + " not found")
        );

        checkAdminOnly();

        post.setStatus(PostStatus.PUBLISHED);

        return postMapper.toPostResponseDto(postRepository.save(post));
    }


    @Transactional(readOnly = true)
    public Page<PostResponseDto> getPostDrafts(Pageable pageable){
        User currentUser = getCurrentUser();

        return postRepository.findByStatusAndUserOrderByCreatedAtDesc(PostStatus.DRAFT,currentUser, pageable)
                .map(postMapper::toPostResponseDto);

    }


    private void checkOwnershipOrAdmin(Post post){
        User currentUser = getCurrentUser();

        if(!post.getUser().equals(currentUser) && !currentUser.getRole().equals(Roles.ADMIN)){
            throw new AccessDeniedException("You can only manage your own posts or use admin privileges");
        }
    }

    private void checkAdminOnly(){
        User currentUser = getCurrentUser();

        if(!currentUser.getRole().equals(Roles.ADMIN)){
            throw new AccessDeniedException("Admin privileges required");
        }
    }

    private User getCurrentUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || auth.getPrincipal() == null){
            throw new ResourceNotFoundException("No authenticated user found");
        }

        return (User) auth.getPrincipal() ;
    }


}
