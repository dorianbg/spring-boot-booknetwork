package com.webapp;

import com.webapp.model.*;
import com.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookNetwork implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private FavouriteService favouriteService;

    public static void main(String[] args) {
        SpringApplication.run(BookNetwork.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // create users
        User newAdmin = new User("admin@admin.com", "admin", "admin");
        userService.createAdmin(newAdmin);
        User newUser = new User("test@t.com", "tester", "t");
        userService.createUser(newUser);
        User newUser2 = new User("florence@gmail.com", "Flo", "t");
        userService.createUser(newUser2);
        User newUser3 = new User("jacksterling@t.com", "Jack Sterling", "t");
        userService.createUser(newUser3);

        // create authors
        Author author1 = new Author();
        author1.setName("Oscar Wilde");
        authorService.saveAuthor(author1);

        Author author2 = new Author();
        author2.setName("Mark Twain");
        authorService.saveAuthor(author2);

        Author author3 = new Author();
        author3.setName("Charles Dickens");
        authorService.saveAuthor(author3);

        Author author4 = new Author();
        author4.setName("Agatha Christie");
        authorService.saveAuthor(author4);

        Author author5 = new Author();
        author5.setName("Virginia Woolf");
        authorService.saveAuthor(author5);

        Author author6 = new Author();
        author6.setName("Jane Austen");
        authorService.saveAuthor(author6);

        // create books
        Book book = new Book();
        book.setName("The Picture of Dorian Gray");
        book.setDescription("The Picture of Dorian Gray is a Gothic and philosophical novel by Oscar Wilde, first published complete in the July 1890.");
        book.setAuthor(author1);
        bookService.saveBook(book);

        Book book2 = new Book();
        book2.setName("De Profundis");
        book2.setDescription("De Profundis is a letter written by Oscar Wilde during his imprisonment in Reading Gaol, to \"Bosie\". ");
        book2.setAuthor(author1);
        bookService.saveBook(book2);

        Book book3 = new Book();
        book3.setName("Adventures of Huckleberry Finn");
        book3.setDescription("DescriptionAdventures of Huckleberry Finn is a novel by Mark Twain, first published in the United Kingdom in December 1884 and in the United States in February 1885.");
        book3.setAuthor(author2);
        bookService.saveBook(book3);

        Book book4 = new Book();
        book4.setName("The Adventures of Tom Sawyer");
        book4.setDescription("The Adventures of Tom Sawyer by Mark Twain is an 1876 novel about a young boy growing up along the Mississippi River. It is set in the 1840s in the fictional town of St. Petersburg, inspired by Hannibal, Missouri, where Twain lived as a boy. In the novel Tom Sawyer has several adventures, often with his friend, Huck.");
        book4.setAuthor(author2);
        bookService.saveBook(book4);

        Book book5 = new Book();
        book5.setName("Pride and Prejudice");
        book5.setDescription("Pride and Prejudice is an 1813 romantic novel by Jane Austen. It charts the emotional development of protagonist Elizabeth Bennet, who learns the error of making hasty judgments and comes to appreciate the difference between the superficial and the essential.");
        book5.setAuthor(author6);
        bookService.saveBook(book5);

        Book book6 = new Book();
        book6.setName("Persuasion");
        book6.setDescription("Persuasion is the last novel fully completed by Jane Austen. It was published at the end of 1817, six months after her death. The story concerns Anne Elliot, a young Englishwoman of 27 years, whose family is moving to lower their expenses and get out of debt. They rent their home to an Admiral and his wife.");
        book6.setAuthor(author6);
        bookService.saveBook(book6);

        // add comments
        Comment comment = new Comment(newUser, book);
        comment.setText("This is a great book !");
        commentService.saveComment(comment);

        Comment comment2 = new Comment(newUser2, book);
        comment2.setText("Great book !");
        commentService.saveComment(comment2);

        Comment comment3 = new Comment(newUser3, book);
        comment3.setText("Very interesting in the start but boring near the end.");
        commentService.saveComment(comment3);

        Comment comment4 = new Comment(newUser, book);
        comment4.setText("Beautiful piece but I didn't like the end");
        commentService.saveComment(comment4);

        Comment comment5 = new Comment(newUser, book2);
        comment5.setText("A lengthy book");
        commentService.saveComment(comment5);

        Comment comment6 = new Comment(newUser, book3);
        comment6.setText("Marvelous piece");
        commentService.saveComment(comment6);

        // add ratings
        Rating rating = new Rating(newUser, book);
        rating.setValue(5);
        ratingService.saveRating(rating);

        Rating rating2 = new Rating(newUser, book2);
        rating2.setValue(4);
        ratingService.saveRating(rating2);

        Rating rating3 = new Rating(newUser, book3);
        rating3.setValue(4);
        ratingService.saveRating(rating3);

        Rating rating4 = new Rating(newUser, book4);
        rating4.setValue(2);
        ratingService.saveRating(rating4);

        Rating rating5 = new Rating(newUser, book2);
        rating5.setValue(1);
        ratingService.saveRating(rating5);

        Rating rating6 = new Rating(newUser, book3);
        rating6.setValue(5);
        ratingService.saveRating(rating6);

        Rating rating7 = new Rating(newUser, book4);
        rating7.setValue(5);
        ratingService.saveRating(rating7);

        Rating rating8 = new Rating(newUser, book2);
        rating8.setValue(3);
        ratingService.saveRating(rating8);

        Rating rating9 = new Rating(newUser, book3);
        rating9.setValue(4);
        ratingService.saveRating(rating9);

        Rating rating10 = new Rating(newUser, book5);
        rating10.setValue(3);
        ratingService.saveRating(rating10);

        // add favourites
        favouriteService.saveFavourite(new Favourite(newUser, book));
        favouriteService.saveFavourite(new Favourite(newUser2, book));
        favouriteService.saveFavourite(new Favourite(newUser3, book));
        favouriteService.saveFavourite(new Favourite(newUser, book2));
        favouriteService.saveFavourite(new Favourite(newUser, book3));
        favouriteService.saveFavourite(new Favourite(newUser, book4));
        favouriteService.saveFavourite(new Favourite(newUser, book5));
        favouriteService.saveFavourite(new Favourite(newUser2, book2));
        favouriteService.saveFavourite(new Favourite(newUser2, book3));
        favouriteService.saveFavourite(new Favourite(newUser2, book5));
        favouriteService.saveFavourite(new Favourite(newUser3, book));
        favouriteService.saveFavourite(new Favourite(newUser3, book2));
        favouriteService.saveFavourite(new Favourite(newUser3, book3));
        favouriteService.saveFavourite(new Favourite(newUser3, book5));
    }
}
