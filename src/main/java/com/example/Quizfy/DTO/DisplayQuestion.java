package com.example.Quizfy.DTO;

public class DisplayQuestion {

    private int qId;
//    private int sessionId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

//    public int getSessionId() {
//        return sessionId;
//    }
//
//    public void setSessionId(int sessionId) {
//        this.sessionId = sessionId;
//    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

//    public void setId(int id) {
//    }
}
