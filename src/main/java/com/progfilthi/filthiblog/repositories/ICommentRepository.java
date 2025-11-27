package com.progfilthi.filthiblog.repositories;

import com.progfilthi.filthiblog.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository<Comment, Long> {
}
