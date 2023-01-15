package com.sda.controllers;

import com.sda.exceptions.ResourceNotFoundException;
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
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    AuthorService authorService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Quiz> createQuizAndAddToUser(@PathVariable Long userId, @RequestBody Quiz quiz) {
        // Create quiz entity
        Quiz newQuiz = quizService.createQuiz(quiz ,userId);
        //Save the quiz entity
        quizService.saveQuiz(newQuiz);
        return new ResponseEntity<Quiz>(newQuiz, HttpStatus.CREATED);
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

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable long id){
            Quiz quiz = quizService.findQuizById(id);
            return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/userQuizzes/{id}")
    public ResponseEntity<List<Quiz>> getAll(@PathVariable long id){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        quizzes.removeIf(quiz -> quiz.getAuthor().getId()!=id);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/all-quizzes")
    public ResponseEntity<List<Quiz>> getAll(){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

}
