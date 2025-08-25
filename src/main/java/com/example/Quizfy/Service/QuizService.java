package com.example.Quizfy.Service;

import com.example.Quizfy.DTO.*;
import com.example.Quizfy.Enum.Option;
import com.example.Quizfy.Exception.CustomException;
import com.example.Quizfy.Model.AttemptQuestion;
import com.example.Quizfy.Model.Question;
import com.example.Quizfy.Model.UserSession;
import com.example.Quizfy.Repository.AttemptRepository;
import com.example.Quizfy.Repository.QuestionRepository;
import com.example.Quizfy.Repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizrepository;

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
        user.setCount(0);
        quizrepository.save(user);
        return "User saved with sessionId: " + sessionId;
    }

    private String generateUniqueSessionId() {
        String sessionId;
        Random random = new Random();

        do {
            int number = 100000 + random.nextInt(900000);
            sessionId = String.valueOf(number);
        } while (quizrepository.existsBySessionId(sessionId));

        return sessionId;
    }

    public String saveQuestion(List<Question> questions) {
        questionrepository.saveAll(questions);
        return "Questions saved successfully.";
    }

    public DisplayQuestion getRandomUnattemptedQuestion(String sessionId) {
        UserSession user = quizrepository.findBySessionId(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid session ID"));

        if (user.getCount() >= 5) {
            throw new IllegalStateException("User has already attempted 5 questions");
        }

        Question selected = questionrepository.findRandomUnattemptedQuestion(sessionId);

        if (selected == null) {
            throw new IllegalStateException("No more unattempted questions available");
        }

        return convertToDTO(selected);
    }


    private DisplayQuestion convertToDTO(Question q) {
        DisplayQuestion dto = new DisplayQuestion();
        dto.setqId(q.getId());
        dto.setQuestion(q.getQuestion());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        return dto;
    }

    public void submitAnswer(AttemptRequest attempt) {
        UserSession user = quizrepository.findBySessionId(attempt.getSessionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid session ID"));

        if (user.getCount() >= 5) {
            throw new IllegalStateException("Maximum attempts reached");
        }

        Question question = questionrepository.findById(attempt.getQuestionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid question ID"));

        AttemptQuestion aq = new AttemptQuestion();
        aq.setUser(user);
        aq.setQuestion(question);
        aq.setCorrect(attempt.getSelectedOption().equals(question.getCorrectAnswer()));
        aq.setUserAnswer(attempt.getSelectedOption());
        attemptRepo.save(aq);

        user.setCount(user.getCount() + 1);
        quizrepository.save(user);
    }

    public ShowResult displayResult(String id) throws CustomException {
        if (id.isEmpty()) {
            throw new CustomException("601", "enter id");
        }
        UserSession user;
        try {
            Optional<UserSession> us = quizrepository.findBySessionId(id);
            if (!us.isPresent()) {
                throw new CustomException("602", "id is null");
            }
            user = us.get();
        } catch (IllegalArgumentException e) {
            throw new CustomException("603", "enter valid id");
        } catch (Exception e) {
            throw new CustomException("604", "something went wrong" + e.getMessage());
        }

        String uid = user.getSessionId();

        List<AttemptQuestion> attempted;
        try {
            List<AttemptQuestion> aq = attemptRepo.findQuestion(uid);
            if (aq == null || aq.isEmpty()) {
                throw new CustomException("605", "No attempts found for this session");
            }
            attempted = aq;
        } catch (Exception e) {
            throw new CustomException("606", "something went wrong" + e.getMessage());
        }


        List<Integer> qid = attemptRepo.findQid(uid);

        if (qid.isEmpty() || qid.size() < 5) {
            throw new CustomException("607", "Complete your test then you can show the result");
        }

        List<Question> questions = questionrepository.findQue(qid);

        if (questions.isEmpty() || questions.size() < 5) {
            throw new CustomException("608", "Complete your test then you can show the result");
        }

        int countTrue = attemptRepo.getCountTrue(uid);
        int countFalse = attemptRepo.getCountFalse(uid);

        List<QuestionDTO> quesDto = new ArrayList<>();
        for (Integer quid : qid) {
            QuestionDTO qd = new QuestionDTO();
            Object[] row = (Object[]) questionrepository.getRawQuestionData(quid);
            qd.setId(quid);
            qd.setQuestion((String) row[0]);
            qd.setOptionA((String) row[1]);
            qd.setOptionB((String) row[2]);
            qd.setOptionC((String) row[3]);
            qd.setOptionD((String) row[4]);
            qd.setCorrectAns(Option.valueOf(String.valueOf(row[5])));

            Object[] row2 = (Object[]) attemptRepo.getRaw2QuestionData(quid, uid);
            qd.setCorrect((Boolean) row2[0]);
            qd.setUserAns(Option.valueOf(String.valueOf(row2[1])));

            quesDto.add(qd);
        }

        attemptRepo.deleteEntry(uid);
        UserSession us = new UserSession();
        quizrepository.updateCount(uid);

        return new ShowResult(uid, quesDto, countTrue, countFalse);
    }
}