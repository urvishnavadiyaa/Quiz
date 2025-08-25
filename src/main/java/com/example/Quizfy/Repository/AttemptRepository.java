package com.example.Quizfy.Repository;

import com.example.Quizfy.Model.AttemptQuestion;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttemptRepository extends JpaRepository<AttemptQuestion, Integer> {

    @Query("SELECT a.question.id FROM AttemptQuestion a WHERE a.user.id = :userId")
    List<Integer> findAttemptedQuestionIdsByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM attempt WHERE session_id = ?1", nativeQuery = true)
    List<AttemptQuestion> findQuestion(String uid);

    @Query(value = "SELECT q_id FROM attempt WHERE session_id = ?1", nativeQuery = true)
    List<Integer> findQid(String uid);

    @Query(value = "SELECT COUNT(*) FROM attempt WHERE is_correct = 1 AND session_id = ?1", nativeQuery = true)
    int getCountTrue(String uid);

    @Query(value = "SELECT COUNT(*) FROM attempt WHERE is_correct = 0 AND session_id = ?1", nativeQuery = true)
    int getCountFalse(String uid);

    @Query(value = "SELECT is_correct, user_ans FROM attempt WHERE q_id = ?1 AND session_id = ?2", nativeQuery = true)
    Object getRaw2QuestionData(int qid, String uid);

    @Modifying
    @Transactional
    @Query(value = "delete from attempt where session_id = ?1", nativeQuery = true)
    void deleteEntry(String uid);
}
