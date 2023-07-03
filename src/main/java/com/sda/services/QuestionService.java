package com.sda.services;

import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Answer;
import com.sda.models.Question;
import com.sda.models.Quiz;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private AnswerRepository answerRepository;

    public Question findQuestionById(long id) {
        return questionRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Question not found"));
    }

    public Question createQuestion (Question q){
        Question newQuestion = new Question();
        newQuestion.setStatement(q.getStatement());
        newQuestion.setAvailable(true);
        List<Answer> answers = new ArrayList<>(); // Correct answer availability might not be defined as true
        for (Answer a : q.getAnswers() ) {
            a.setAvailable(true);
            answerRepository.save(a);
            answers.add(a);
        }
        newQuestion.setCorrectAnswer(q.getCorrectAnswer());
        answerRepository.save(q.getCorrectAnswer());
        newQuestion.setAnswers(answers);
        return questionRepository.save(newQuestion);
    }

    public Quiz addQuestionToQuiz (Question question, long quizID){
        Quiz quiz = quizRepository.findById(quizID).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        quiz.getQuestions().add(createQuestion(question));
        return quizRepository.save(quiz);
    }

    public Question editQuestion(long id, Question question) {
        Question questionToUpdate = findQuestionById(id);

        questionToUpdate.setStatement(question.getStatement());
        questionToUpdate.setAnswers(question.getAnswers());
        for (Answer a : questionToUpdate.getAnswers()) {
            a.setAvailable(true);
            answerRepository.saveAndFlush(a);
        }
        questionToUpdate.setCorrectAnswer(questionToUpdate.getAnswers().get(0));

        saveQuestion(questionToUpdate);
        return questionToUpdate;
    }

    private void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    public void disableQuestion(long questionId) {
        Question question = findQuestionById(questionId);
        question.setAvailable(!question.isAvailable());
        for (Answer a : question.getAnswers()) {
            a.setAvailable(false);
            answerRepository.saveAndFlush(a);
        }
        saveQuestion(question);
    }
}
