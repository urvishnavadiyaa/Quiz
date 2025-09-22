package com.example.Quizfy.DTO.Request;

import com.example.Quizfy.Enum.Option;
import lombok.Data;

@Data
public class AttemptRequest {
    private String sessionId;
    private int questionId;
    private Option selectedOption;

}
