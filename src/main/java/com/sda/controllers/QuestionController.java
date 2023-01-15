package com.sda.controllers;

import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.services.QuestionService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @PostMapping("/create/{quizId}")
    public ResponseEntity<Question> createQuestionAndAddToQuiz(@PathVariable long quizId, @RequestBody Question question) {
        Quiz quiz = quizService.findQuizById(quizId);
        Question newQuestion = questionService.createQuestion(question);
        questionService.saveQuestion(newQuestion);
        questionService.addQuestionToQuiz(quizId, newQuestion);
        quizService.editQuiz(quizId, quiz);
        return new ResponseEntity<Question>(newQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<String> removeQuestionById(@PathVariable long id) {
        questionService.changeWQuestionStatusToUnavailable(id);
        return new ResponseEntity<String>("The quiz with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }



}
