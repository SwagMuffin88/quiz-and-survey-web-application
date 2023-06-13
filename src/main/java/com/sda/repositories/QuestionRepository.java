package com.sda.repositories;

import com.sda.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository <Question,Long> {
}
