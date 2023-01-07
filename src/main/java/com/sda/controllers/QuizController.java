package com.sda.controllers;

import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        //check if there is user with the provided ID first
        Author author = authorService.findAuthorById(userId);
        // Create quiz entity
        Quiz newQuiz = quizService.createQuiz(quiz);
        //Save the quiz entity
        quizService.saveQuiz(newQuiz);
        //add quiz to the quizzes list of the user
        quizService.addQuizToAuthor(author.getUsername(), quiz.getQuizTitle());
        // and save the changes in the DB
        authorService.updateAuthor(userId, author); // <- might be redundant
        return new ResponseEntity<Quiz>(quiz, HttpStatus.CREATED);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteQuizById (@PathVariable int id ){
//        quizService.deleteQuiz(id);
//        return new ResponseEntity<String>(" the quiz with the ID "+id+" is removed",
//                HttpStatus.NO_CONTENT);
//    }


}
