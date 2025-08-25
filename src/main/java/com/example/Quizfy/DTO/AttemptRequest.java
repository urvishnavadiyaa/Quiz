package com.example.Quizfy.DTO;

import com.example.Quizfy.Enum.Option;

public class AttemptRequest {
    private String sessionId;
    private int questionId;
    private Option selectedOption;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public Option getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(Option selectedOption) {
        this.selectedOption = selectedOption;
    }
}
