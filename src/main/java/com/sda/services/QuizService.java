package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.repositories.QuizRepository;
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

    public Quiz createQuiz( Quiz quiz) {
        List<Question> quizQuestions = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            List<Answer> answersList = new ArrayList<>();
            Question newQuestion = new Question();
            newQuestion.setQuestionStatement(question.getQuestionStatement());
            for (Answer a : question.getAnswers()) {
                answerRepository.save(a);
                answersList.add(a);
            }
            newQuestion.setAnswers(answersList);
            newQuestion.setCorrectAnswer(question.getAnswers().get(0).getAnswerStatement());
            questionRepository.save(newQuestion);
            quizQuestions.add(newQuestion);
        }
        Quiz newQuiz = new Quiz();
        newQuiz.setQuizTitle(quiz.getQuizTitle());
        newQuiz.setQuizDescription(quiz.getQuizDescription());
        newQuiz.setQuestions(quizQuestions);
        newQuiz.setPrivateStatus(quiz.isPrivateStatus());
        return newQuiz;
    }

    public Quiz findQuizById(long quizId) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        return quiz;
    }

    public Quiz editQuiz(long quizId, Quiz quiz) {
        Quiz quizToUpdate = findQuizById(quizId);
        quizToUpdate.setQuizTitle(quiz.getQuizTitle());
        quizToUpdate.setQuizDescription(quiz.getQuizDescription());
        quizToUpdate.setPrivateStatus(quiz.isPrivateStatus());

        saveQuiz(quizToUpdate);
        return quizToUpdate;
    }

    public void saveQuiz (Quiz quiz) {
        quizRepository.save(quiz);
    }

    public void addQuizToAuthor(String username, String quizTitle) {
        Author author = authorRepository.findByUsername(username);
        Quiz quiz = quizRepository.findByQuizTitle(quizTitle);
        if (quiz != null) author.getQuizzes().add(quiz);
    }
    public void disableQuiz(long quizId) {
        Quiz quiz = findQuizById(quizId);
        quiz.setAvailable(!quiz.isAvailable());
    }

    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }
}
