package com.example.Quizfy.DTO.Response;

import com.example.Quizfy.Enum.Option;
import lombok.Data;

@Data
public class QuestionDTO {
    private int id;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private Option correctAns;
    private Option userAns;
    private Boolean isCorrect;

}
