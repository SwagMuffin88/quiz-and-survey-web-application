package com.sda.controllers;

import com.sda.models.Question;
import com.sda.models.Quiz;
import com.sda.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/add/{id}")
    public ResponseEntity<Quiz> addQuestion (@PathVariable Long id, @RequestBody Question question){
        Quiz quiz = questionService.addQuestionToQuiz(question,id);
        return new ResponseEntity<>(quiz, HttpStatus.CREATED);
    }
    @PostMapping("/remove/{id}")
    public ResponseEntity<Question> editQuestionStatus (@PathVariable Long id){
        Question question = questionService.editAvailableStatus(id);
        return new ResponseEntity<>(question, HttpStatus.CREATED);
    }
}
