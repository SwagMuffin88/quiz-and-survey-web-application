package com.sda.controllers;

import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    AuthorService authorService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Quiz> createQuizAndAddToUser(@PathVariable Long userId, @RequestBody Quiz quiz) {
        System.out.println(quiz.toString());
        //check if there is user with the provided ID first
        Author author = authorService.findAuthorById(userId);
        // Create quiz entity
        Quiz newQuiz = quizService.createQuiz(quiz);
        //Save the quiz entity
        quizService.saveQuiz(newQuiz);
        //add quiz to the quizzes list of the user
        quizService.addQuizToAuthor(author.getUsername(), quiz.getQuizTitle());
        // and save the changes in the DB
        authorService.updateAuthor(userId, author);
        return new ResponseEntity<Quiz>(quiz, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Quiz> editQuizById(@PathVariable long id, @RequestBody Quiz quiz) {
        Quiz updatedQuiz = quizService.editQuiz(id, quiz);
        return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<String> removeQuizById(@PathVariable long id) {
        quizService.disableQuiz(id);
        return new ResponseEntity<String>("The quiz with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable long id){
        Quiz quiz = quizService.findQuizById(id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Quiz>> getAll(){
        List<Quiz> quizs = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizs, HttpStatus.OK);
    }



}
