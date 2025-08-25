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

    private int count = 0;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
