package com.sda.controllers;

import com.sda.Repositories.AnswerRepository;
import com.sda.Repositories.AuthorRepository;
import com.sda.Repositories.QuestionRepository;
import com.sda.Repositories.QuizRepository;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/create-quiz")
public class QuizController {
    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    AnswerRepository answerRepository;

    @PostMapping("/create/{id}")
    public ResponseEntity<Quiz> createQuiz (@PathVariable int id , @RequestBody Quiz quiz){

        List<Question> quizQuestions = new ArrayList<>();
        for (Question question: quiz.getQuestions()) {
            List<Answer> answersList = new ArrayList<>();
            Question newQuestion = new Question();
            newQuestion.setQuestionStatement(question.getQuestionStatement());
            for (Answer a: question.getAnswers()) {
                answerRepository.save(a);
                answersList.add(a);
            }
            newQuestion.setAnswers(answersList);
            newQuestion.setCorrectAnswer(question.getAnswers().get(0).getAnswerStatement());
            questionRepository.save(newQuestion);
            quizQuestions.add(newQuestion);
        }
        Quiz q = new Quiz(quiz.getQuizTitle(),quiz.getQuizDescription(),quizQuestions,quiz.isPrivateStatus());
        quizRepository.save(q);
        Author author = authorRepository.findById(id).
                orElseThrow(()-> new RuntimeException("The user with the ID "+id+" not found "));
        author.addQuiz2QuizList(q);
        authorRepository.save(author);
        return new ResponseEntity<Quiz>(q, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuizById (@PathVariable int id ){
        quizRepository.deleteById(id);
        return new ResponseEntity<String>(" the quiz with the "+id+" is removed", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAll (){
        quizRepository.deleteAll();
        questionRepository.deleteAll();
        answerRepository.deleteAll();
        return new ResponseEntity<String>(" data base clean", HttpStatus.NO_CONTENT);
    }

}
