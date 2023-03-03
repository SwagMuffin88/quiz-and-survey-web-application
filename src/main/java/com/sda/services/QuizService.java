package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.model.users.Participant;
import com.sda.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class QuizService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    @Autowired
    @Lazy
    private QuestionService questionService;

    public Quiz createQuizAndAddAuthor(Quiz quiz , long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(()-> new ResourceNotFoundException("Author not found"));
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
        newQuiz.setPublic(quiz.isPublic());
        newQuiz.setTags(quiz.getTags());
        return newQuiz;
    }

    public Quiz findQuizById(long quizId) {
        return quizRepository.findById(quizId).orElseThrow(
                () -> new ResourceNotFoundException("Quiz not found"));
    }

    public Quiz editQuiz(long quizId, Quiz quiz) throws Exception {
        Quiz quizToUpdate = findQuizById(quizId);
        if (quizToUpdate.getParticipantList().size() == 0) {
            quizToUpdate.setTitle(quiz.getTitle());
            quizToUpdate.setDescription(quiz.getDescription());
            quizToUpdate.setCategory(quiz.getCategory());
            quizToUpdate.setPublic(quiz.isPublic());
            saveQuiz(quizToUpdate);
            return quizToUpdate;
        }
        else throw new Exception("Cannot modify a quiz if it has participants!");
    }

    public void saveQuiz (Quiz quiz) {
        quizRepository.save(quiz);
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

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }
}
