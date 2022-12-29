package com.sda.Repositories;


import com.sda.model.quizzes.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Integer> {
}
