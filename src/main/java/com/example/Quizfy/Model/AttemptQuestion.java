package com.example.Quizfy.Model;

import com.example.Quizfy.Enum.Option;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}
