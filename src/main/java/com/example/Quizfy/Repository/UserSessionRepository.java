package com.example.Quizfy.Repository;

import com.example.Quizfy.Model.UserSession;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Integer> {

    boolean existsBySessionId(String sessionId);

    @Query(value = "select * from  user_session where session_id = ?1", nativeQuery = true)
    UserSession findBySessionId(String sessionId);

    @Modifying
    @Transactional
    @Query(value = "update user_session set atmp_count = 0, last_sub = 0, sub_count = 0 where session_id = ?1", nativeQuery = true)
    void updateCount(String uid);

}
