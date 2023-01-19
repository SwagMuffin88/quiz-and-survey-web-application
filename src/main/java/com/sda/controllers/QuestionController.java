package com.sda.controllers;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.services.QuestionService;
import com.sda.services.QuizService;
import lombok.SneakyThrows;
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

    @SneakyThrows
    @PostMapping("/create/{quizId}") // Password protected
    public ResponseEntity<Question> createQuestionAndAddToQuiz(@PathVariable long quizId, @RequestBody Question question) {
        Quiz quiz = quizService.findQuizById(quizId);
        Question newQuestion = questionService.createQuestion(question);
        questionService.saveQuestion(newQuestion);
        questionService.addQuestionToQuiz(quizId, newQuestion);
        quizService.editQuiz(quizId, quiz);
        return new ResponseEntity<Question>(newQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}/edit") //Password protected
    public ResponseEntity<Question> editQuestion(@PathVariable long questionId, @RequestBody Question question) {
        return new ResponseEntity<>(questionService.editQuestion(questionId, question), HttpStatus.OK);
    }

    @PutMapping("/{questionId}/remove") // Password protected
    public ResponseEntity<String> removeQuestionById(@PathVariable long questionId) {
        questionService.disableQuestion(questionId);
        return new ResponseEntity<>(
                "The question with ID " + questionId + " is removed", HttpStatus.NO_CONTENT);
    }

}
