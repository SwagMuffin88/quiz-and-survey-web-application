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

    @PutMapping("remove/{id}")
    public ResponseEntity<String> removeQuestion (@PathVariable Long id) {
        questionService.disableQuestion(id);
        return new ResponseEntity<>("Question with id " + id + " removed", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Question> editQuestion (@PathVariable Long id, @RequestBody Question question) {
        Question questionToUpdate = questionService.editQuestion(id, question);
        return new ResponseEntity<>(questionToUpdate, HttpStatus.OK);
    }
}
