package com.sda.services;

import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Author;
import com.sda.models.Question;
import com.sda.models.Quiz;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private QuestionService questionService;


    public Quiz createQuizAndAddAuthor(Quiz quiz , long authorId){
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("Author no found"));
        List<Question> questions = new ArrayList<>();
        for (Question q : quiz.getQuestions()) {
            Question newQuestion = questionService.createQuestion(q);
            questions.add(newQuestion);
        }
        Quiz newQuiz = new Quiz();
        newQuiz.setAuthor(author);
        newQuiz.setAvailable(quiz.isAvailable());
        newQuiz.setQuestions(questions);
        newQuiz.setDescription(quiz.getDescription());
        newQuiz.setCategory(quiz.getCategory());
        newQuiz.setTitle(quiz.getTitle());
        newQuiz.setPublicized(quiz.isPublicized());
        newQuiz.setTags(quiz.getTags());
        return newQuiz;
    }
    public Quiz saveQuiz (Quiz quiz) {
        return quizRepository.save(quiz);
    }
    public void editPublicStatus(long quizId){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        quiz.setPublicized(!quiz.isPublicized());
        saveQuiz(quiz);
    }
    public void editAvailableStatus( long quizId){
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        quiz.setAvailable(!quiz.isAvailable());
        saveQuiz(quiz);
    }

    public List<Quiz> getAllQuizzes ( ){
        return quizRepository.findAll();
    }
    public List<Quiz> getUserQuizzes ( long userID){
        List<Quiz> userQuizzes = getAllQuizzes();
        userQuizzes.removeIf(quiz -> quiz.getAuthor().getId()!=userID);
        userQuizzes.removeIf(quiz -> !quiz.isAvailable());
        return userQuizzes;
    }
    public Quiz getQuizById( long quizId){
        return quizRepository.findById(quizId).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
    }

    public List<Quiz> getAllQuizzesSorted (String property){
        return quizRepository.findAll(Sort.by(property));
    }

    public Page<Quiz> getAllQuizzesWithPagination (int offSet ,int pageSize ){
//        List<Quiz> userQuizzes = getAllQuizzes();
//        userQuizzes.removeIf(quiz -> quiz.isPublic()==false);
//        Pageable pageable = PageRequest.of(offSet, pageSize);
//        Page<Quiz> page = new PageImpl<>(userQuizzes, pageable, userQuizzes.size());

       Page<Quiz> quizPage = quizRepository.findPublicQuizzes(PageRequest.of(offSet, pageSize));

        return quizPage;
    }
}
