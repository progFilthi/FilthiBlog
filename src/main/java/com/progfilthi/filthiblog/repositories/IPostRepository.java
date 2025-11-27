package com.progfilthi.filthiblog.repositories;

import com.progfilthi.filthiblog.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
