package com.testingstudy.testingstudy.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User,Long> {


    @Query("" +
            "SELECT CASE WHEN COUNT(s) > 0 THEN " +
            "TRUE ELSE FALSE END " +
            "FROM User s " +
            "WHERE s.email = ?1"
    )
    Boolean selectExistsEmail(String email);


}
