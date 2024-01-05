package com.krishna.marketplace.repository;

import org.springframework.stereotype.Repository;

import com.krishna.marketplace.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoryRespository extends JpaRepository<Category, Long> {

    

}
