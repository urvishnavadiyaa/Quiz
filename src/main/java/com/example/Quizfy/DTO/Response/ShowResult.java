package com.example.Quizfy.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowResult {
    private String id;
    private List<QuestionDTO> qDto;
    private int correctCount;
    private int inCorrectCount;

}
