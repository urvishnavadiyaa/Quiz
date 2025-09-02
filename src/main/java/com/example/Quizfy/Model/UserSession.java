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
    private int atmpCount = 0;
    private int subCount = 0;
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
}
