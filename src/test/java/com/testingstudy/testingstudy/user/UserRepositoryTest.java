package com.testingstudy.testingstudy.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void testSelectExistsEmail_whenEmailExist_ShouldReturnTrue() {
        //given
        User user = new User(
                "Kaan",
                "kaan@hotmail.com",
                Gender.FEMALE
        );
        underTest.save(user);
        //when
        Boolean expected = underTest.selectExistsEmail(user.getEmail());
        //than
        assertThat(expected).isTrue();
    }

    @Test
    void testSelectExistsEmail_whenEmailNotExist_ShouldReturnFalse() {
        //given
        String email = "kaan@hotmail.com";
        //when
        Boolean expected = underTest.selectExistsEmail(email);
        //than
        assertThat(expected).isFalse();
    }
}