package com.example.Quizfy.Controller;

import com.example.Quizfy.DTO.*;
import com.example.Quizfy.Exception.ControllerException;
import com.example.Quizfy.Exception.CustomException;
import com.example.Quizfy.Model.Question;
import com.example.Quizfy.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    QuizService quizservice;

    @PostMapping("/save-one")
    public ResponseEntity<String> saveUsers(@RequestBody UserSessionDTO request) {
        try {
            return ResponseEntity.ok(quizservice.saveUsers(request.getName()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid argument: " + e.getMessage());
        }
    }

    @PostMapping("/save-question")
    public ResponseEntity<String> saveQuestion(@RequestBody List<Question> question) {
        try {
            return ResponseEntity.ok(quizservice.saveQuestion(question));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid argument: " + e.getMessage());
        }
    }

    @GetMapping("/random-question/{sessionId}")
    public ResponseEntity<?> getRandomQuestion(@PathVariable String sessionId) {
        try {
            DisplayQuestion q = quizservice.getRandomUnattemptedQuestion(sessionId);
            return ResponseEntity.ok(q);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/get-question")
//    public ResponseEntity<?> getQuestion(@RequestBody AttemptRequest attemptRequest) {
//        try {
//            DisplayQuestion q = quizservice.nextQuestion(AttemptRequest);
//            return ResponseEntity.ok(q);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/submit-answer")
    public ResponseEntity<String> submitAnswer(@RequestBody AttemptRequest attempt) {
        try {
            quizservice.submitAnswer(attempt);
            return ResponseEntity.ok("Answer submitted");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/display-result")
    public ResponseEntity<?> display(@RequestBody UserSessionDTO id) {
        try {
            ShowResult ce = quizservice.displayResult(id.getSessionId());
            return new  ResponseEntity<>(ce,HttpStatus.OK);
        } catch (CustomException e) {
            ControllerException conE = new ControllerException(e.getErrorCode(), e.getErrorMessage());
            return new ResponseEntity<>(conE, HttpStatus.BAD_REQUEST);
        }
    }

}
