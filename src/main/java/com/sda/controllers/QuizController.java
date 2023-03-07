package com.sda.controllers;

import com.sda.models.Quiz;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addQuiz (@PathVariable Long id, @RequestBody Quiz quiz){
        Quiz newQuiz = quizService.createQuizAndAddAuthor(quiz,id);
        quizService.saveQuiz(newQuiz);
        return new ResponseEntity<>("added", HttpStatus.CREATED);
    }
    @PutMapping("/public/{id}")
    public ResponseEntity<String> editPublicStatus (@PathVariable Long id){
        quizService.editPublicStatus(id);
        return new ResponseEntity<>("edited", HttpStatus.OK);
    }
    @PutMapping("/remove/{id}")
    public ResponseEntity<String> editAvailableStatus (@PathVariable Long id){
        quizService.editAvailableStatus(id);
        return new ResponseEntity<>("edited", HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<List<Quiz>> getUserQuizzes (@PathVariable Long id){
        List<Quiz> userQuizzes = quizService.getUserQuizzes(id);
        return  new ResponseEntity<>(userQuizzes,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Quiz> getQuiz (@PathVariable Long id){
        Quiz quiz = quizService.getQuizById(id);
        return  new ResponseEntity<>(quiz,HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quiz>> getAllQuizzes (){
        List<Quiz> Quizzes = quizService.getAllQuizzes();
        Quizzes.removeIf(quiz -> quiz.isPublic()==false);
        return  new ResponseEntity<>(Quizzes,HttpStatus.OK);
    }

    @GetMapping("/sortedby/{proprety}")
    public ResponseEntity<List<Quiz>> getAllQuizzesSorted(@PathVariable String proprety){
        List<Quiz> Quizzes = quizService.getAllQuizzesSorted(proprety);
        Quizzes.removeIf(quiz -> quiz.isPublic()==false);
        return  new ResponseEntity<>(Quizzes,HttpStatus.OK);
    }
    @GetMapping("/pagination/{offSet}/{pageSize}")
    public ResponseEntity<Page<Quiz>> getAllQuizzesWithPagination(@PathVariable int pageSize, @PathVariable int offSet){
        Page<Quiz> quizPage = quizService.getAllQuizzesWithPagination(offSet,pageSize);
        return  new ResponseEntity<>(quizPage,HttpStatus.OK);
    }


}
