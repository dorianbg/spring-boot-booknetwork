package com.webapp.service;

import com.webapp.model.Book;
import com.webapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAllBooks(){
        return this.bookRepository.findAll();
    }

    public void saveBook(Book book){
        this.bookRepository.save(book);
    }

    public Book findById(Long id){
        return this.bookRepository.findById(id);
    }

    public List<Book> findByAuthorId(Long id){
        return this.bookRepository.findByAuthorId(id);
    }

    public void deleteById(Long id){
        Book book = this.bookRepository.findById(id);
        this.bookRepository.delete(book);
    }
}
