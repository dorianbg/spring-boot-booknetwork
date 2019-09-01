package com.webapp.service;

import com.webapp.model.Rating;
import com.webapp.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public List<Rating> findAllRatings(){
        return this.ratingRepository.findAll();
    }

    public List<Rating> findAllRatingsByUserEmail(String email){
        return this.ratingRepository.findAllByUserEmail(email);
    }

    public List<Rating> findAllRatingsByBookId(Long id){
        return this.ratingRepository.findAllByBookId(id);
    }

    public Rating findRatingByBookIdAndUserEmail(Long id, String email) {
        return this.ratingRepository.findByBookIdAndUserEmail(id, email);
    }

    public void saveRating(Rating rating){
        this.ratingRepository.save(rating);
    }

    public void deleteRating(Long bookId, String userEmail){
        this.ratingRepository.delete(this.findRatingByBookIdAndUserEmail(bookId, userEmail));
    }

    public void deleteRating(Rating rating){
        this.ratingRepository.delete(rating);
    }

}
