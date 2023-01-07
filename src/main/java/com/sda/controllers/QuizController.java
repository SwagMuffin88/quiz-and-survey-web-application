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
@RequestMapping("/create-quiz")
public class QuizController {
    @Autowired
    AuthorService authorService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Quiz> createQuizAndAddToUser(@PathVariable Long userId,
                                                       @RequestBody Quiz quiz) {
        quizService.createQuiz(quiz);

        Author author = authorService.findAuthor(userId).
                orElseThrow(()-> new RuntimeException("The user with the ID "+ userId +" not found "));

        quizService.addQuizToAuthor(author.getUsername(), quiz.getQuizTitle());
        authorService.updateAuthor(userId, author); // <- might be redundant
        return new ResponseEntity<Quiz>(quiz, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuizById (@PathVariable int id ){
        quizService.deleteQuiz(id);
        return new ResponseEntity<String>(" the quiz with the ID "+id+" is removed",
                HttpStatus.NO_CONTENT);
    }

    // Might need a different method (not removing from database)
//    @DeleteMapping("/deleteAll")
//    public ResponseEntity<String> deleteAll (){
//        quizRepository.deleteAll();
//        questionRepository.deleteAll();
//        answerRepository.deleteAll();
//        return new ResponseEntity<String>(" data base clean", HttpStatus.NO_CONTENT);
//    }

}
