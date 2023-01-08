package com.sda.controllers;

import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.services.QuestionService;
import com.sda.services.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/question")
public class QuestionController {
    private QuestionService questionService;
    private QuizService quizService;

    @PostMapping("/create/{quizId}")
    public ResponseEntity<Question> createQuestionAndAddToQuiz(
            @PathVariable long quizId, @RequestBody Question question) {

        System.out.println("1");
        Quiz quiz = quizService.findQuizById(quizId);
        System.out.println("2");
        Question newQuestion = questionService.createQuestion(question);
        questionService.saveQuestion(newQuestion);
        questionService.addQuestionToQuiz(quizId, newQuestion);

        quizService.editQuiz(quizId, quiz);
        return new ResponseEntity<Question>(newQuestion, HttpStatus.CREATED);
    }
}
