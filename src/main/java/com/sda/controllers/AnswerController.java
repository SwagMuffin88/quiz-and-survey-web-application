package com.sda.controllers;

//import org.springframework.web.bind.annotation.CrossOrigin;
import com.sda.exception.ResourceNotFoundException;
import com.sda.models.Answer;
import com.sda.models.Question;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.services.AnswerService;
import com.sda.services.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    @PutMapping("/{id}/edit")
    public ResponseEntity<Answer> editAnswer(@PathVariable long id, @RequestBody Answer answer) {
        return new ResponseEntity<>(answerService.editAnswer(id, answer), HttpStatus.OK);
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<String> removeAnswerById(@PathVariable long id) {
        answerService.disableAnswer(id);
        return new ResponseEntity<>(
                "The answer with ID " + id + " is removed", HttpStatus.NO_CONTENT);

    }

}
