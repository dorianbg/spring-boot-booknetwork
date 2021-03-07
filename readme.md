## BookNetwork

BookNetwork is a social networking site for people who love reading and always have their face in a book.
On BookNetwork you can rate, comment and favourite books. You can also read the comments made by others on their favourite books and other books by their favourite authors.

### Techonologies
- back-end: Spring Boot  
- data layer: JPA and Hibernate (ORM)
- front-end: Thymeleaf, HTML, CSS 

### System design

The application will be accessed using a internet browser.  
It was designed under the expectation of a moderate user base for which a single server would be capable of handling the load.

The application is designed in the traditional multilayered architecture consisting of:  
1) Presentation layer (view layer)  
2) Application layer (controller layer)  
3) Domain layer (service layer)  
4) Persistence layer (DAO layer)  
  
The presentation layer contains the user interfaces (UI) for the end users which allow the user to interact with the application.  

The application layer handles and validates the user requests before forwarding them to appropriate services in the domain layer. The application layer also populates the UI for the user.   

The domain layer contains domain specific business logic that is executed on top of domain specific entities in the persistence layer.  

The persistence layer handles all database related operations and serves the domain layer.   

In this design the presentation layer never communicates directly with the persistence layer.
The separation of logical parts avoids tight coupling and promotes reusability.
This design provides separation of concerns and simplicity.  

This design could also be interpreted as a three tiered architecture since we have a presentation tier, a logic tier and a data tier. In the presentation tier each client request always goes through the logic tier which fetches data from the data tier and then updates the presentation tier.  


### Features

Users can:  
- [x] register (using an email) 
- [x] securely login  
- [x] view authors  
- [x] add an author
- [x] for each author view the authors books     
- [x] add a book under an author  
- [x] for each book view its description, author, number of ratings, average rating, list of comments made on that book, number of times it was favourited and view other members of the site who listed the book as being among their favourite books   
- [x] comment on a book and update the comment  
- [x] rate a book and change (or remove) their rating  
- [x] favourite a book and remove the favourite  

Administrators can additionally:  
- [x] delete users (which also deletes their comments, ratings, favourites and security roles)  
- [x] create, delete and update authors  
- [x] create, delete and update books  
- [x] create, delete and update comments       

### Image demonstrations

![Alt text](images/0.jpg?raw=true "Title")

![Alt text](images/1.jpg?raw=true "Title")

![Alt text](images/2.jpg?raw=true "Title")

![Alt text](images/3.jpg?raw=true "Title")

![Alt text](images/4.jpg?raw=true "Title")

![Alt text](images/5.jpg?raw=true "Title")

![Alt text](images/6.jpg?raw=true "Title")