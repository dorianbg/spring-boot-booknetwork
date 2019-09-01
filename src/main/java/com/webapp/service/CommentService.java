package com.webapp.service;

import com.webapp.model.Comment;
import com.webapp.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    public List<Comment> findAllComments(){
        return this.commentRepository.findAll();
    }

    public List<Comment> findAllCommentsByUserEmail(String email){
        return this.commentRepository.findAllByUserEmail(email);
    }

    public List<Comment> findAllCommentsByBookId(Long id){
        return this.commentRepository.findAllByBookId(id);
    }

    public Comment findCommentByBookIdAndUserEmail(Long id, String email) {
        return commentRepository.findByBookIdAndUserEmail(id, email);
    }

    public void saveComment(Comment comment){
        this.commentRepository.save(comment);
    }


    public void deleteComment(Long bookId, String userEmail){
        this.commentRepository.delete(this.findCommentByBookIdAndUserEmail(bookId, userEmail));
    }

}
