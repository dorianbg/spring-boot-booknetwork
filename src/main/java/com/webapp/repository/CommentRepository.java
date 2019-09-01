package com.webapp.repository;

import com.webapp.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByUserEmail(String email);

    List<Comment> findAllByBookId(Long id);

    Comment findByBookIdAndUserEmail(Long id, String email);

}
