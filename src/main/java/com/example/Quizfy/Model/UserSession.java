package com.example.Quizfy.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
