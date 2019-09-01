package com.webapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "ratings")
@Table(name = "ratings")
public class Rating {

    @EmbeddedId
    private RatingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("userEmail")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("bookId")
    private Book book;

    @Column(name = "value")
    private Integer value;

    private Rating() {}

    public Rating(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new RatingId(user.getEmail(), book.getId());
    }

    public RatingId getId() {
        return id;
    }

    public void setId(RatingId id) {
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

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rating rating = (Rating) o;
        return id.equals(rating.id) &&
                user.equals(rating.user) &&
                book.equals(rating.book) &&
                value.equals(rating.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, book, value);
    }


    @Embeddable
    public static class RatingId implements Serializable {

        @Column(name = "userEmail")
        private String userEmail;

        @Column(name = "bookId")
        private Long bookId;

        private RatingId() {}

        public RatingId(String userEmail, Long bookId) {
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
            RatingId ratingId = (RatingId) o;
            return userEmail.equals(ratingId.userEmail) &&
                    bookId.equals(ratingId.bookId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userEmail, bookId);
        }
    }
}