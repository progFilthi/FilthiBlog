package com.progfilthi.filthiblog.repositories;

import com.progfilthi.filthiblog.enums.PostStatus;
import com.progfilthi.filthiblog.models.Post;
import com.progfilthi.filthiblog.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStatusOrderByCreatedAtDesc(PostStatus status, Pageable pageable);
    Page<Post> findByStatusAndUserOrderByCreatedAtDesc(PostStatus status, User user, Pageable pageable);
    Page<Post> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
}
