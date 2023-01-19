package com.sda.controllers;

import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    AuthorService authorService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<Quiz> createQuizForAuthor(@PathVariable Long userId, @RequestBody Quiz quiz) {
        // Create quiz entity
        Quiz newQuiz = quizService.createQuizAndAddAuthor(quiz ,userId);  // Method assigns author to quiz
        //Save the quiz entity
        quizService.saveQuiz(newQuiz);
        return new ResponseEntity<Quiz>(newQuiz, HttpStatus.CREATED);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Quiz> editQuizById(@PathVariable long id, @RequestBody Quiz quiz) {
        if (quiz.getParticipantList().size() == 0) {
            Quiz updatedQuiz = quizService.editQuiz(id, quiz);
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
        } else { // If quiz has participants already, it cannot be modified.
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<String> removeQuizById(@PathVariable long id) {
        quizService.disableQuiz(id);
        // What will happen to the list of participants?
        return new ResponseEntity<String>("The quiz with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable long id){
        Quiz quiz = quizService.findQuizById(id);
        //This is to shuffle the answers
        List<Question> questionList= quiz.getQuestions();
        for (Question q :questionList) {
            Collections.shuffle(q.getAnswers());
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/userQuizzes/{userId}")
    public ResponseEntity<List<Quiz>> getAllUserQuizzes(@PathVariable long userId){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        quizzes.removeIf(quiz -> quiz.getAuthor().getId()!= userId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/all-quizzes")
    public ResponseEntity<List<Quiz>> getAll(){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        // Filter quizzes by availability
        List<Quiz> availableQuizzes = new ArrayList<>();
        for (Quiz q : quizzes) {
            if (q.isAvailable()) availableQuizzes.add(q);
        }
        return new ResponseEntity<>(availableQuizzes, HttpStatus.OK);
    }

}
