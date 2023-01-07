package com.sda.repositories;

import com.sda.model.quizzes.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Integer> {
    Quiz findByQuizTitle(String quizTitle);
}
