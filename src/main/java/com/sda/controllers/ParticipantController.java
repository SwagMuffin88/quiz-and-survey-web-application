package com.sda.controllers;

import com.sda.models.Participant;
import com.sda.models.Quiz;
import com.sda.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    ParticipantService participantService;

    @PostMapping("/add/{id}")
    public ResponseEntity<Quiz> saveParticipant(@PathVariable long id , @RequestBody Participant participant){
        Quiz quiz = participantService.addParticipantToQuiz(participant,id);
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }


}
