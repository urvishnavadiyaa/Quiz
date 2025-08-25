package com.example.Quizfy.Model;

import com.example.Quizfy.Enum.Option;
import jakarta.persistence.*;

@Entity
@Table(name = "attempt")
public class AttemptQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "session_id")
    private UserSession user;

    @ManyToOne
    @JoinColumn(name = "q_id")
    private Question question;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_ans")
    private Option userAnswer;

    public int getId() {
        return id;
    }

    public Option getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(Option userAnswer) {
        this.userAnswer = userAnswer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserSession getUser() {
        return user;
    }

    public void setUser(UserSession user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }
}
