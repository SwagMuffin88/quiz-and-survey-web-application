package com.sda.repositories;

import com.sda.models.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuizRepository extends JpaRepository<Quiz,Long> {

    @Query("SELECT q FROM Quiz q WHERE q.publicized = true")
    Page<Quiz> findPublicQuizzes(Pageable pageable);

}
