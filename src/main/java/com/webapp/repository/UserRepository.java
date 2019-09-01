package com.webapp.repository;

import java.util.List;

import com.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, String> {

	List<User> findByNameLike(String name);

	User findByEmail(String email);

	User findByName(String name);

}
