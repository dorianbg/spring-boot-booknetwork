package com.webapp.repository;

import com.webapp.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByUserEmail(String email);

    List<Rating> findAllByBookId(Long id);

    Rating findByBookIdAndUserEmail(Long id, String email);
}
