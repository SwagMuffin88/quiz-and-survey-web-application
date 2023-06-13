package com.sda.services;

import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Answer;
import com.sda.repositories.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer editAnswer(long answerId, Answer answer) {
        Answer answerToUpdate = findAnswerById(answerId);
        answerToUpdate.setStatement(answer.getStatement());
        answerToUpdate.setAvailable(true);
        saveAnswer(answerToUpdate);
        return answerToUpdate;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    private Answer findAnswerById(long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new ResourceNotFoundException("Answer not found"));
    }

    public void disableAnswer(long answerId) {
        Answer answer = findAnswerById(answerId);
        answer.setAvailable(false);
        saveAnswer(answer);
    }
}
