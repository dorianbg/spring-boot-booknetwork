package com.webapp.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "comments")
@Table(name = "comments")
public class Comment {

    @EmbeddedId
    private CommentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("userEmail")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("bookId")
    private Book book;

    @Column(name = "text")
    private String text;

    private Comment() {}

    public Comment(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new CommentId(user.getEmail(), book.getId());
    }

    public CommentId getId() {
        return id;
    }

    public void setId(CommentId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id.equals(comment.id) &&
                user.equals(comment.user) &&
                book.equals(comment.book) &&
                text.equals(comment.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, book, text);
    }


    @Embeddable
    public static class CommentId implements Serializable {

        @Column(name = "userEmail")
        private String userEmail;

        @Column(name = "bookId")
        private Long bookId;

        private CommentId() {}

        public CommentId(String userEmail, Long bookId) {
            this.userEmail = userEmail;
            this.bookId = bookId;
        }

        public String getUserEmail() {
            return userEmail;
        }

        public Long getBookId() {
            return bookId;
        }

        public void setUserEmail(String userEmail) {
            this.userEmail = userEmail;
        }

        public void setBookId(Long bookId) {
            this.bookId = bookId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CommentId commentId = (CommentId) o;
            return userEmail.equals(commentId.userEmail) &&
                    bookId.equals(commentId.bookId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userEmail, bookId);
        }
    }
}