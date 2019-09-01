package com.webapp.repository;


import com.webapp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findById(Long id);
    List<Book> findByAuthorId(Long id);
}

