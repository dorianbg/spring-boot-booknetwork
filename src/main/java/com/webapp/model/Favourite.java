package com.webapp.model;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "favourites")
@Table(name = "favourites")
public class Favourite {

    @EmbeddedId
    private FavouriteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("userEmail")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("bookId")
    private Book book;

    private Favourite() {}

    public Favourite(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new FavouriteId(user.getEmail(), book.getId());
    }

    public FavouriteId getId() {
        return id;
    }

    public void setId(FavouriteId id) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Favourite favouriteId = (Favourite) o;
        return id.equals(favouriteId.id) &&
                user.equals(favouriteId.user) &&
                book.equals(favouriteId.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, book);
    }


    @Embeddable
    public static class FavouriteId implements Serializable {

        @Column(name = "userEmail")
        private String userEmail;

        @Column(name = "bookId")
        private Long bookId;

        private FavouriteId() {}

        public FavouriteId(String userEmail, Long bookId) {
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
            FavouriteId favouriteId = (FavouriteId) o;
            return userEmail.equals(favouriteId.userEmail) &&
                    bookId.equals(favouriteId.bookId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(userEmail, bookId);
        }
    }
}