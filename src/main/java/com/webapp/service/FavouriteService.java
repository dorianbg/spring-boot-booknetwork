package com.webapp.service;

import com.webapp.model.Favourite;
import com.webapp.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {
    @Autowired
    FavouriteRepository favouriteRepository;

    public List<Favourite> findAllFavourites(){
        return this.favouriteRepository.findAll();
    }

    public List<Favourite> findAllFavouritesByUserEmail(String email){
        return this.favouriteRepository.findAllByUserEmail(email);
    }

    public List<Favourite> findAllFavouritesByBookId(Long id){
        return this.favouriteRepository.findAllByBookId(id);
    }

    public Favourite findFavouriteByBookIdAndUserEmail(Long id, String email) {
        return this.favouriteRepository.findByBookIdAndUserEmail(id, email);
    }

    public void saveFavourite(Favourite favourite){
        this.favouriteRepository.save(favourite);
    }

    public void deleteFavourite(Long bookId, String userEmail){
        this.favouriteRepository.delete(this.findFavouriteByBookIdAndUserEmail(bookId, userEmail));
    }

}
