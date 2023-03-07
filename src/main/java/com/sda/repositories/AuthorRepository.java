package com.sda.repositories;

import com.sda.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AuthorRepository extends JpaRepository<Author,Long>{

    Optional<Author> findByUsername(String username);
    Optional<Author> findByUsernameOrEmail(String username, String email);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
