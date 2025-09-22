package com.example.Quizfy.Service;

import com.example.Quizfy.DTO.*;
import com.example.Quizfy.Enum.Option;
import com.example.Quizfy.Exception.CustomException;
import com.example.Quizfy.Model.AttemptQuestion;
import com.example.Quizfy.Model.Question;
import com.example.Quizfy.Model.UserSession;
import com.example.Quizfy.Repository.AttemptRepository;
import com.example.Quizfy.Repository.QuestionRepository;
import com.example.Quizfy.Repository.UserSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuizService {
    @Autowired
    UserSessionRepository userSessionRepository;

    @Autowired
    QuestionRepository questionrepository;

    @Autowired
    AttemptRepository attemptRepo;

    public String saveUsers(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        UserSession user = new UserSession();
        user.setName(name);
        String sessionId = generateUniqueSessionId();
        user.setSessionId(sessionId);
        user.setAtmpCount(0);
        user.setSubCount(0);
        user.setLastSub(0);
        userSessionRepository.save(user);
        return "User saved with sessionId: " + sessionId;
    }

    private String generateUniqueSessionId() {
        String sessionId;
        Random random = new Random();

        do {
            int number = 100000 + random.nextInt(900000);
            sessionId = String.valueOf(number);
        } while (userSessionRepository.existsBySessionId(sessionId));

        return sessionId;
    }

    public String saveQuestion(List<Question> questions) {
        questionrepository.saveAll(questions);
        return "Questions saved successfully.";
    }

    public DisplayQuestion getRandomUnattemptedQuestion(String sessionId) {
        UserSession user = userSessionRepository.findBySessionId(sessionId);
        if (user == null) {
            throw new IllegalStateException("Invalid session ID");
        }
        if (user.getAtmpCount() != user.getSubCount()) {
            throw new IllegalStateException("you must submit the answer");
        }
        if (user.getSubCount() == 5) {
            throw new IllegalStateException("User has already attempted 5 questions");
        }

        Question selected = questionrepository.findRandomUnattemptedQuestion(sessionId);

        if (selected == null) {
            throw new IllegalStateException("No more unattempted questions available");
        }
        user.setAtmpCount(user.getAtmpCount() + 1);
        user.setLastSub(selected.getQId());
        userSessionRepository.save(user);
        return convertToDTO(selected);
    }


    private DisplayQuestion convertToDTO(Question q) {
        DisplayQuestion dto = new DisplayQuestion();
        dto.setQId(q.getQId());
        dto.setQuestion(q.getQuestion());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        return dto;
    }

    public void submitAnswer(AttemptRequest attempt) {
        UserSession user = userSessionRepository.findBySessionId(attempt.getSessionId());
        if (user == null) {
            throw new IllegalStateException("Invalid session ID");
        }

        Question atmpQ = attemptRepo.findQuestionByUserAndQuestion(user.getSessionId(), attempt.getQuestionId());

        if (atmpQ != null) {
            throw new IllegalStateException("question is already attempted by this user");
        }
        if (user.getLastSub() != attempt.getQuestionId()) {
            throw new IllegalStateException("that is not attempted by user");
        }
        if (user.getSubCount() >= 5) {
            throw new IllegalStateException("Maximum attempts reached");
        }

        Question question = questionrepository.findById(attempt.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        AttemptQuestion aq = new AttemptQuestion();
        aq.setUser(user);
        aq.setQuestion(question);
        aq.setIsCorrect(attempt.getSelectedOption().equals(question.getCorrectAnswer()));
        aq.setUserAnswer(attempt.getSelectedOption());
        attemptRepo.save(aq);

        user.setSubCount(user.getSubCount() + 1);
        userSessionRepository.save(user);
    }

    public ShowResult displayResult(String id) throws CustomException {
        if (id.isEmpty()) {
            throw new CustomException("601", "enter id");
        }
        try {
            userSessionRepository.findBySessionId(id);
        } catch (IllegalArgumentException e) {
            throw new CustomException("603", "enter valid id");
        } catch (Exception e) {
            throw new CustomException("604", "something went wrong" + e.getMessage());
        }


        List<Integer> qid = attemptRepo.findQid(id);

        if (qid.size() < 5) {
            throw new CustomException("607", "Complete your test then you can show the result");
        }

        List<Question> questions = questionrepository.findQue(qid);

        if (questions.size() < 5) {
            throw new CustomException("608", "Complete your test then you can show the result");
        }

        int countTrue = attemptRepo.getCountTrue(id);
        int countFalse = attemptRepo.getCountFalse(id);

        List<QuestionDTO> quesDto = new ArrayList<>();
        for (Integer quid : qid) {
            QuestionDTO qd = new QuestionDTO();
            Object[] row = (Object[]) questionrepository.getRawQuestionData(quid);
            qd.setId(quid);
            qd.setQuestion(String.valueOf(row[0]));
            qd.setOptionA(String.valueOf(row[1]));
            qd.setOptionB(String.valueOf(row[2]));
            qd.setOptionC(String.valueOf(row[3]));
            qd.setOptionD(String.valueOf(row[4]));
            qd.setCorrectAns(Option.valueOf(String.valueOf(row[5])));

            Object[] row2 = (Object[]) attemptRepo.getRaw2QuestionData(quid, id);
            qd.setIsCorrect((Boolean) row2[0]);
            qd.setUserAns(Option.valueOf(String.valueOf(row2[1])));
            quesDto.add(qd);
        }

        attemptRepo.deleteEntry(id);
        UserSession us = new UserSession();
        userSessionRepository.updateCount(id);

        return new ShowResult(id, quesDto, countTrue, countFalse);
    }
}