package com.example.Quizfy.Repository;

import com.example.Quizfy.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query(value = "select * from question where q_id IN (?1)", nativeQuery = true)
    List<Question> findQue(List<Integer> qid);

    @Query("SELECT q FROM Question q WHERE q.qId NOT IN :ids")
    List<Question> findUnattemptedQuestions(@Param("ids") List<Integer> ids);

    @Query(value = """ 
            SELECT * FROM question WHERE q_id NOT IN (
            SELECT q_id FROM attempt WHERE session_id = :sessionId)
             ORDER BY RAND() LIMIT 1""", nativeQuery = true)
    Question findRandomUnattemptedQuestion(@Param("sessionId") String sessionId);

    @Query(value = "SELECT question, option_a, option_b, option_c, option_d, correct_answer FROM question WHERE q_id = ?1", nativeQuery = true)
    Object getRawQuestionData(int qid);
}
