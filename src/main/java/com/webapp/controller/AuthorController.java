package com.webapp.controller;

import com.webapp.model.Author;
import com.webapp.service.AuthorService;
import com.webapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class AuthorController {
    @Autowired
    AuthorService authorService;

    @Autowired
    BookService bookService;

    @GetMapping(value = "/author")
    public String getAuthorsList(ModelMap map) {
        map.addAttribute("authorList", authorService.findAllAuthors());
        return "listings/listAuthors";
    }

    @GetMapping(value = "/author/{id}")
    public String getAuthorDetails(@PathVariable Long id, ModelMap map) {
        Author author = authorService.findById(id);
        map.addAttribute("author", author);
        return "listings/listAuthorsBooks";
    }

    @GetMapping(value = "/author/create")
    public String createAuthorForm(Model model, HttpSession session) {
        Author author = new Author();
        model.addAttribute("author", author);
        model.addAttribute("action", "create");
        return "forms/createOrUpdateAuthor";
    }

    @PostMapping(value = "/author/create")
    public String createAuthorForm(@Valid Author author, BindingResult bindingResult, HttpSession session) {
        authorService.saveAuthor(author);
        return "redirect:/author";
    }

    @GetMapping(value = "/author/update/{id}")
    public String updateAuthorForm(Model model, @PathVariable Long id) {
        model.addAttribute("author", authorService.findById(id));
        model.addAttribute("action", "update");
        model.addAttribute("authorId", id);
        return "forms/createOrUpdateAuthor";
    }

    @PostMapping(value = "/author/update/{id}")
    public String updateAuthorForm(@Valid Author author, BindingResult bindingResult, HttpSession session, @PathVariable Long id) {
        Author originalAuthor = authorService.findById(id);
        originalAuthor.setName(author.getName());
        authorService.saveAuthor(originalAuthor);
        return "redirect:/author";
    }

    @GetMapping(value = "/author/delete/{id}")
    public String deleteAuthor(@PathVariable Long id) {
        this.authorService.deleteById(id);
        return "redirect:/author";
    }

}

