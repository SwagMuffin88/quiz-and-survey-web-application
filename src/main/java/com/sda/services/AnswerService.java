package com.sda.services;

import com.sda.repositories.AnswerRepository;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class AnswerService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
}
