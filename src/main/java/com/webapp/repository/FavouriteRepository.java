package com.webapp.repository;

import com.webapp.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findAllByUserEmail(String email);

    List<Favourite> findAllByBookId(Long id);

    Favourite findByBookIdAndUserEmail(Long id, String email);
}
