package com.krishna.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.marketplace.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
