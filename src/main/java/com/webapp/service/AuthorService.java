package com.webapp.service;

import com.webapp.model.Author;
import com.webapp.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public List<Author> findAllAuthors(){
        return authorRepository.findAll();
    }

    public void saveAuthor(Author author){
        this.authorRepository.save(author);
    }

    public Author findByName(String name){
        return this.authorRepository.findByName(name);
    }

    public Author findById(Long id){
        return this.authorRepository.findById(id);
    }

    public void deleteById(Long id){
        Author author = this.authorRepository.findById(id);
        this.authorRepository.delete(author);
    }

}
