package com.woongs.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description : Quiz VO의 DB처리를 위한 레파지토리
 * @author Woongs
 * @sicne 18.10.24
 */
public interface QuizRepository extends JpaRepository<Quiz, Long> {

}
