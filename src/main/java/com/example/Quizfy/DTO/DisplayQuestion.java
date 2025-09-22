package com.example.Quizfy.DTO;

import lombok.Data;

@Data
public class DisplayQuestion {

    private int qId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

}
