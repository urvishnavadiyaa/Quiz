package com.example.Quizfy.DTO;

import java.util.List;

public class ShowResult {
    private String id;
    private List<QuestionDTO> qDto;
    private int correctCount;
    private int inCorrectCount;

    public ShowResult(String id, List<QuestionDTO> qDto, int correctCount, int inCorrectCount) {
        this.id = id;
        this.qDto = qDto;
        this.correctCount = correctCount;
        this.inCorrectCount = inCorrectCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<QuestionDTO> getqDto() {
        return qDto;
    }

    public void setqDto(List<QuestionDTO> qDto) {
        this.qDto = qDto;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getInCorrectCount() {
        return inCorrectCount;
    }

    public void setInCorrectCount(int inCorrectCount) {
        this.inCorrectCount = inCorrectCount;
    }
}
