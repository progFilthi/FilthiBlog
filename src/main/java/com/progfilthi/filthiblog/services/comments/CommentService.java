package com.progfilthi.filthiblog.services.comments;

import com.progfilthi.filthiblog.globalExceptionHandler.ResourceNotFoundException;
import com.progfilthi.filthiblog.mappers.ICommentMapper;
import com.progfilthi.filthiblog.models.Comment;
import com.progfilthi.filthiblog.models.Post;
import com.progfilthi.filthiblog.models.User;
import com.progfilthi.filthiblog.models.dto.comment.CommentResponseDto;
import com.progfilthi.filthiblog.models.dto.comment.CreateCommentDto;
import com.progfilthi.filthiblog.models.dto.comment.UpdateCommentDto;
import com.progfilthi.filthiblog.repositories.ICommentRepository;
import com.progfilthi.filthiblog.repositories.IPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final ICommentRepository commentRepository;
    private final ICommentMapper commentMapper;
    private final IPostRepository postRepository;

    @Transactional
    public CommentResponseDto createComment(Long post_id, CreateCommentDto dto){
        Comment comment = commentMapper.toCommentEntity( dto);


        Post post = postRepository.findById(post_id).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found")
        );

        comment.setPost(post);

        User currentUser = getCurrentUser();

        comment.setUser(currentUser);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponseDto(savedComment);

    }


    @Transactional(readOnly = true)
    public Page<CommentResponseDto> getAllComments(Pageable pageable){
        return commentRepository.findAll(pageable).map(commentMapper::toCommentResponseDto);
    }

    @Transactional
    public CommentResponseDto updateComment(Long comment_id, UpdateCommentDto dto){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found.")
        );

        commentMapper.updateFromUpdateCommentDto(dto, comment);

        Comment updatedComment = commentRepository.save(comment);

        return commentMapper.toCommentResponseDto(updatedComment);
    }


    @Transactional
    public void deleteComment(Long comment_id){
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found.")
        );

        commentRepository.delete(comment);
    }



    private User getCurrentUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if(auth == null || auth.getPrincipal() == null){
            throw new ResourceNotFoundException("Authenticated user not found");
        }

        return (User) auth.getPrincipal();
    }

}
