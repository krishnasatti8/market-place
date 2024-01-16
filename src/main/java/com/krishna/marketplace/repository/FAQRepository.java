package com.krishna.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.marketplace.model.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

}
