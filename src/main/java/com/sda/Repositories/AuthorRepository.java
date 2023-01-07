package com.sda.Repositories;


import com.sda.model.users.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author,Integer> {

    Optional<Author> findByEmail(String email);
}
