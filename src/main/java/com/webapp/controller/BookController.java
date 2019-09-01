package com.webapp.controller;

import com.webapp.model.*;
import com.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class BookController {
    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    RatingService ratingService;

    @Autowired
    FavouriteService favouriteService;

    @GetMapping(value = "/book/")
    public String listAllBooks(ModelMap map) {
        map.addAttribute("bookList", bookService.findAllBooks());
        return "listings/listAuthorsBooks";
    }

    @GetMapping(value = "/book/{bookId}")
    public String getBookInformation(ModelMap map, @PathVariable Long bookId, Principal principal) {
        Book book = bookService.findById(bookId);
        List<Comment> commentList = commentService.findAllCommentsByBookId(bookId);
        User user = userService.findByEmail(principal.getName());

        Rating ratingByUser = ratingService.findRatingByBookIdAndUserEmail(bookId, principal.getName());
        if (ratingByUser == null){
            ratingByUser = new Rating(user, book);
            ratingByUser.setValue(null);
        }
        List<Rating> ratings = ratingService.findAllRatingsByBookId(bookId);
        int sum = 0;
        int numRatings = 0;
        for (Rating rating : ratings){
            Integer ratingValue = rating.getValue();
            if (ratingValue != null) {
                sum += ratingValue;
                numRatings += 1;
            }
        }
        String averageRating;
        if (numRatings > 0){
            averageRating = new DecimalFormat("0.00").format((float) sum/numRatings);
        }
        else{
            averageRating = "No ratings yet. Please rate this book.";
        }

        List<Favourite> favourites = favouriteService.findAllFavouritesByBookId(bookId);
        String numberFavourites = String.valueOf(favourites.size());

        map.addAttribute("book", book);
        map.addAttribute("comments", commentList);
        map.addAttribute("author", book.getAuthor());
        map.addAttribute("user", user);
        if (favouriteService.findFavouriteByBookIdAndUserEmail(bookId, principal.getName()) != null) {
            map.addAttribute("userFavourited", "");
        }
        map.addAttribute("rating", ratingByUser);
        map.addAttribute("average_rating", averageRating);
        map.addAttribute("number_ratings", numRatings);
        map.addAttribute("number_favourites", numberFavourites);
        if (commentService.findCommentByBookIdAndUserEmail(bookId, principal.getName()) != null) {
            map.addAttribute("userAddedComment", "");
        }

        return "listings/listBookInformation";
    }

    /*
    Book related CRUD
     */
    @GetMapping(value = "/book/create/{authorId}")
    public String createBook(@PathVariable Long authorId, Model model, HttpSession session) {
        model.addAttribute("book", new Book());
        model.addAttribute("action", "create");
        model.addAttribute("authorId", authorId);
        return "forms/createOrUpdateBook";
    }

    @PostMapping(value = "/book/create/{authorId}")
    public String createBook(@PathVariable Long authorId, @Valid Book book, BindingResult bindingResult, HttpSession session) {
        Author author = authorService.findById(authorId);
        book.setAuthor(author);
        bookService.saveBook(book);
        return "redirect:/author/{id}".replace("{id}", Long.toString(author.getId()));
    }

    @GetMapping(value = "/book/update/{id}")
    public String updateBook(@PathVariable Long id, ModelMap map) {
        map.addAttribute("book", bookService.findById(id));
        map.addAttribute("action", "update");
        map.addAttribute("bookId", id);
        return "forms/createOrUpdateBook";
    }

    @PostMapping(value = "/book/update/{id}")
    public String updateBook(@Valid Book book, @PathVariable Long id) {
        Book originalBook = bookService.findById(id);
        originalBook.setDescription(book.getDescription());
        originalBook.setName(book.getName());
        originalBook.setAuthor(authorService.findById(originalBook.getAuthor().getId()));
        bookService.saveBook(originalBook);
        return "redirect:/author/" + originalBook.getAuthor().getId();
    }

    @GetMapping(value = "/book/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        Book book = this.bookService.findById(id);
        Author author = book.getAuthor();
        author.removeBook(book);
        bookService.deleteById(id);
        return "redirect:/author/" + author.getId();
    }

    /*
    Comment related CRUD
     */
    @GetMapping(value = "/book/{bookId}/comment/create")
    public String createComment(@PathVariable Long bookId, Model model, HttpSession session, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        Book book = bookService.findById(bookId);
        Comment comment = new Comment(user, book);
        model.addAttribute("comment", comment);
        model.addAttribute("action", "create");
        return "forms/createOrUpdateComment";
    }

    @PostMapping(value = "/book/comment/create")
    public String createComment(@Valid Comment tempComment, BindingResult bindingResult, HttpSession session, Principal principal) {
        return persistFormComment(tempComment);
    }

    @GetMapping(value = "/book/{bookId}/{userName}/comment/update")
    public String updateComment(@PathVariable Long bookId, @PathVariable String userName, Model model, HttpSession session, Principal principal) {
        User user = userService.findByName(userName);
        Book book = bookService.findById(bookId);
        Comment comment = commentService.findCommentByBookIdAndUserEmail(book.getId(),user.getEmail());
        model.addAttribute("comment", comment);
        model.addAttribute("action", "update");
        return "forms/createOrUpdateComment";
    }

    @PostMapping(value = "/book/comment/update")
    public String updateComment(@Valid Comment tempComment, BindingResult bindingResult, HttpSession session, Principal principal) {
        return persistFormComment(tempComment);
    }

    @GetMapping(value = "/book/comment/delete/{bookId}/{userName}")
    public String deleteComment(@PathVariable Long bookId, @PathVariable String userName, Model model, HttpSession session) {
        String userEmail = userService.findByName(userName).getEmail();
        Comment comment = commentService.findCommentByBookIdAndUserEmail(bookId, userEmail);
        commentService.deleteComment(bookId, userEmail);
        return "redirect:/book/{bookId}".replace("{bookId}", Long.toString(bookId));
    }

    private String persistFormComment(@Valid Comment tempComment) {
        Comment finalComment = new Comment(tempComment.getUser(), tempComment.getBook());
        finalComment.setText(tempComment.getText());
        commentService.saveComment(finalComment);
        return "redirect:/book/{bookId}".replace("{bookId}", Long.toString(finalComment.getBook().getId()));
    }

    /*
    Rating related CRUD
    */
    @PostMapping(value = "/book/rating")
    public String updateRating(@Valid Rating tempRating, BindingResult bindingResult, HttpSession session, Principal principal) {
        if (tempRating.getValue() == null){
            ratingService.deleteRating(tempRating.getBook().getId(), tempRating.getUser().getEmail());
        }
        else{
            Rating finalRating = new Rating(tempRating.getUser(), tempRating.getBook());
            finalRating.setValue(tempRating.getValue());
            ratingService.saveRating(finalRating);
        }
        return "redirect:/book/{bookId}".replace("{bookId}", Long.toString(tempRating.getBook().getId()));
    }


    /*
    Favourite related CRUD
    */
    @GetMapping(value = "/book/{bookId}/favourite")
    public String favourite(@PathVariable Long bookId, Model model, HttpSession session, Principal principal) {
        favouriteService.saveFavourite(new Favourite(userService.findByEmail(principal.getName()), bookService.findById(bookId)));
        return "redirect:/book/{bookId}".replace("{bookId}", Long.toString(bookId));
    }

    @GetMapping(value = "/book/{bookId}/unfavourite")
    public String unfavourite(@PathVariable Long bookId, Model model, HttpSession session, Principal principal) {
        favouriteService.deleteFavourite(bookId, principal.getName());
        return "redirect:/book/{bookId}".replace("{bookId}", Long.toString(bookId));
    }

    @GetMapping(value = "/book/{bookId}/favouritedby")
    public String usersWhoFavourited(@PathVariable Long bookId, Model model, HttpSession session, Principal principal) {
        Set<User> users = favouriteService.findAllFavouritesByBookId(bookId).stream().map(Favourite::getUser).collect(Collectors.toSet());
        model.addAttribute("usersList", users);
        return "listings/listUsers";
    }
}