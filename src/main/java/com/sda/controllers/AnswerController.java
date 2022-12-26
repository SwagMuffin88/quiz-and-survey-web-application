package com.sda.Controllers;

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
import java.util.Optional;

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
            List<Answer> questionAnswers = new ArrayList<>();
            Question newQuestion = new Question();
            newQuestion.setStatement(question.getStatement());
            for (Answer a: question.getAnswers()) {
                answerRepository.save(a);
                questionAnswers.add(a);
            }
            newQuestion.setAnswers(questionAnswers);
            newQuestion.setCorrectAnswer(questionAnswers.get(0).getStatement());
            questionRepository.save(newQuestion);
            quizQuestions.add(newQuestion);
        }
        Quiz q = new Quiz(quiz.getTitle(),quiz.getDescription(),quizQuestions,quiz.isPrivateStatus());
        quizRepository.save(q);

        Optional<Author> author = authorRepository.findById(id);
        if(author.isPresent()){
            Author author1 = author.get();
            List<Quiz> getListOfUserQuizzes = author1.getQuizzes();
            getListOfUserQuizzes.add(q);
            author1.setQuizzes(getListOfUserQuizzes);
            author1.setId(id);
            authorRepository.save(author1);
            return new ResponseEntity<Quiz>(quiz, HttpStatus.CREATED);
        }else {
            return new ResponseEntity<Quiz>(quiz, HttpStatus.NOT_FOUND);
        }

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
