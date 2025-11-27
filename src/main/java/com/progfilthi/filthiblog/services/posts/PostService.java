package com.progfilthi.filthiblog.services.posts;

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

@Service
@RequiredArgsConstructor
public class PostService {

    private final IPostRepository postRepository;
    private final IPostMapper postMapper;

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

    public Page<PostResponseDto> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable).map(postMapper::toPostResponseDto);
    }

    private User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}
