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

    public Question createQuestion (Question q){
        Question newQuestion = new Question();
        newQuestion.setStatement(q.getStatement());
        newQuestion.setAvailable(true);
        newQuestion.setCorrectAnswer(q.getCorrectAnswer());
        answerRepository.save(q.getCorrectAnswer());
        List<Answer> answers = new ArrayList<>();
        for (Answer a : q.getAnswers() ) {
            a.setAvailable(true);
            answerRepository.save(a);
            answers.add(a);
        }
        newQuestion.setAnswers(answers);
        return questionRepository.save(newQuestion);
    }

    public Quiz addQuestionToQuiz (Question question, long quizID){
        Quiz quiz = quizRepository.findById(quizID).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        quiz.getQuestions().add(createQuestion(question));
        return quizRepository.save(quiz);
    }
    public Question editAvailableStatus (long id){
        Question question = questionRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        question.setAvailable(!question.isAvailable());
        return questionRepository.save(question);
    }

}
