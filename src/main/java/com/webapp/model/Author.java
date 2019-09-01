package com.webapp.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Author implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToMany(
            mappedBy = "author",  // field in books which maps to authors
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Book> books;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addBook(Book book) {
        book.setAuthor(this);
        this.books.add(book);
    }

    public List<Book> getBooks(){
        return this.books;
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.setAuthor(null);
    }

}
