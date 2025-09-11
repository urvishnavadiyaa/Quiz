package com.example.Quizfy.Model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_session")
public class UserSession {

    private String name;

    @Id
    @Column(name = "session_id", unique = true)
    private String sessionId;
    private int atmpCount;
    private int subCount;
    private int lastSub;
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AttemptQuestion> attempts;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public int getAtmpCount() {
        return atmpCount;
    }

    public void setAtmpCount(int atmpCount) {
        this.atmpCount = atmpCount;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }

    public int getLastSub() {
        return lastSub;
    }

    public void setLastSub(int lastSub) {
        this.lastSub = lastSub;
    }

    public UserSession() {
    }

    public UserSession(String name, String sessionId, int atmpCount, int subCount, int lastSub, List<AttemptQuestion> attempts) {
        this.name = name;
        this.sessionId = sessionId;
        this.atmpCount = atmpCount;
        this.subCount = subCount;
        this.lastSub = lastSub;
        this.attempts = attempts;
    }
}
