package com.example.Quizfy.Model;

import com.example.Quizfy.Enum.Option;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qId;

    private String question;

    @Column(name = "option_a")
    private String optionA;

    @Column(name = "option_b")
    private String optionB;

    @Column(name = "option_c")
    private String optionC;

    @Column(name = "option_d")
    private String optionD;

    @Enumerated(EnumType.STRING)
    @Column(name = "correct_answer")
    private Option  correctAnswer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<AttemptQuestion> attempts;

}
