package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.model.users.Participant;
import com.sda.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class QuizService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final ParticipantRepository participantRepository;


    public Quiz createQuiz(Quiz quiz ,long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        List<Question> quizQuestions = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            List<Answer> answersList = new ArrayList<>();
            Question newQuestion = new Question();
            newQuestion.setQuestionStatement(question.getQuestionStatement());
            for (Answer a : question.getAnswers()) {
                a.setAvailable(true);
                answerRepository.save(a);
                answersList.add(a);
            }
            newQuestion.setAnswers(answersList);
            newQuestion.setCorrectAnswer(question.getAnswers().get(0));
            newQuestion.setAvailable(true);
            questionRepository.save(newQuestion);
            quizQuestions.add(newQuestion);
        }
        Quiz newQuiz = new Quiz();
        newQuiz.setAuthor(author);
        newQuiz.setQuizTitle(quiz.getQuizTitle());
        newQuiz.setQuizDescription(quiz.getQuizDescription());
        newQuiz.setQuestions(quizQuestions);
        newQuiz.setPublic(quiz.isPublic());
        newQuiz.setAvailable(true);
        return newQuiz;
    }

    public Quiz findQuizById(long quizId) {
        return quizRepository.findById(quizId).orElseThrow(
                () -> new ResourceNotFoundException("Quiz not found"));
    }

    public Quiz editQuiz(long quizId, Quiz quiz) {
        Quiz quizToUpdate = findQuizById(quizId);
        quizToUpdate.setQuizTitle(quiz.getQuizTitle());
        quizToUpdate.setQuizDescription(quiz.getQuizDescription());
        quizToUpdate.setPublic(quiz.isPublic());
        saveQuiz(quizToUpdate);
        return quizToUpdate;
    }

    public void saveQuiz (Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void addQuizToAuthor(String username, Long quizId) {
        Author author = authorRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("Username not found")); // <- maybe by ID instead // todo
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
                new ResourceNotFoundException("Quiz not found"));

    }

    public List<Participant> getAllParticipantsByQuizId(long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
                new ResourceNotFoundException("Quiz not found"));
        return quiz.getParticipantList();
    }

    public void disableQuiz(long quizId) {
        Quiz quiz = findQuizById(quizId);
        quiz.setAvailable(!quiz.isAvailable());
    }

    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
//        List<Quiz> availbleA = new ArrayList<>();
//        List<Quiz> quizzes= quizRepository.findAll();
//        for (Quiz q:quizzes ) {
//            if(q.isAvailable()){
//                availbleA.add(q);
//            }
//        }
//        return availbleA;
    }
}
