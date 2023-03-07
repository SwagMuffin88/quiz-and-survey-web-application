package com.sda.services;

import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Participant;
import com.sda.models.Quiz;
import com.sda.repositories.ParticipantRepository;
import com.sda.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private ParticipantRepository participantRepository;


    public Quiz addParticipantToQuiz (Participant participant, long id){
        Quiz quiz= quizRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Quiz not found"));
        Participant participant1 = saveParticipant(participant);
        List<Participant> participantList =quiz.getParticipantList();
        participantList.add(participant1);
        quiz.setParticipantList(participantList);
        return quizRepository.save(quiz);
    }

    Participant saveParticipant (Participant participant){
        return participantRepository.save(participant);
    }
}
