package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
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
public class QuestionService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final QuizService quizService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public Question editQuestion(long questionId, Question question) {
        Question questionToUpdate = findQuestionById(questionId);
        questionToUpdate.setQuestionStatement(question.getQuestionStatement());
        questionToUpdate.setCorrectAnswer(question.getCorrectAnswer());

        saveQuestion(questionToUpdate);
        return questionToUpdate;
    }

    private Question findQuestionById(long questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
    }

    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void addQuestionToQuiz(long quizId, Question question) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found"));
        quiz.getQuestions().add(question);
        quizService.saveQuiz(quiz);
    }

    public void disableQuestion(long questionId) {
        Question question = findQuestionById(questionId);
        question.setAvailable(!question.isAvailable());
    }

    public Question createQuestion(Question question) {
        List<Answer> answersList = new ArrayList<>();
        Question newQuestion = new Question();
        newQuestion.setQuestionStatement(question.getQuestionStatement());
        for (Answer a : question.getAnswers()) {
            answerRepository.save(a);
            answersList.add(a);
        }
        newQuestion.setAnswers(answersList);
        newQuestion.setCorrectAnswer(question.getAnswers().get(0).getAnswerStatement());
        newQuestion.setAvailable(true);
        questionRepository.save(newQuestion);

        return newQuestion;
    }
}
