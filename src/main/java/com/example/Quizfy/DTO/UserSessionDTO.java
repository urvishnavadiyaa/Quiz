package com.example.Quizfy.DTO;

import lombok.Data;

@Data
public class UserSessionDTO {
    private String name;
    private String sessionId;
    private int count;

}
