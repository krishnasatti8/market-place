package com.krishna.marketplace.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.krishna.marketplace.enums.UserRole;
import com.krishna.marketplace.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByEmail(String username);

	User findByRole(UserRole userRole);
	
	

}
