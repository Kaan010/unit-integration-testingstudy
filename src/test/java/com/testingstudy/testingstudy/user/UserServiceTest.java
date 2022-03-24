package com.testingstudy.testingstudy.user;

import com.testingstudy.testingstudy.user.exception.BadRequestException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testGetAllUsers_ShouldGetAllUsers() {
        //when
        userService.getAllUsers();
        //then
        Mockito.verify(userRepository).findAll();
    }

    @Test
    void testCreateUser_whenUserEmailExists_ShouldThrowBadRequestException1() {// way 1 to test createUser()
        //given
        User userRequest = new User(
                "testName",
                "testMail",
                Gender.OTHER
        );

        //when
        Mockito.when(userRepository.selectExistsEmail(userRequest.getEmail()))
                .thenThrow(BadRequestException.class);

        //then
        assertThrows(BadRequestException.class,
                () -> userService.createUser(userRequest));
        Mockito.verify(userRepository).selectExistsEmail(userRequest.getEmail());

    }

    @Test//BETTER WAY
    void testCreateUser_whenUserEmailExists_ShouldThrowBadRequestException2() {// way 2 to test createUser()
        //given
        User userRequest = new User(
                "testName",
                "testMail",
                Gender.OTHER
        );
        BDDMockito.given(userRepository.selectExistsEmail(anyString()))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() -> userService.createUser(userRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + userRequest.getEmail() + " taken");
        Mockito.verify(userRepository, Mockito.never()).save(any());

    }

    @Test
    void testCreateUser_whenUserEmailNotExists_ShouldAddGivenUserToDB1() {// way 1 to test createUser()
        //given
        User userRequest = new User(
                "testName",
                "testMail",
                Gender.OTHER
        );

        //when
        Mockito.when(userRepository.selectExistsEmail(userRequest.getEmail()))
                .thenReturn(Boolean.FALSE);
        userService.createUser(userRequest);

        //then
        Mockito.verify(userRepository).selectExistsEmail(userRequest.getEmail());
        Mockito.verify(userRepository).save(userRequest);
    }

    @Test
        //BETTER WAY
    void testCreateUser_whenUserEmailNotExists_ShouldAddGivenUserToDB2() { // way 2 to test createUser()
        //given
        User userRequest = new User(
                "testName",
                "testMail",
                Gender.OTHER
        );

        //when
        userService.createUser(userRequest);

        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(userArgumentCaptor.capture());
        User capturedUser = userArgumentCaptor.getValue();
        assertThat(capturedUser).isEqualTo(userRequest);
    }

    @Test
    void testDeleteUser_whenUserExists_ShouldDeleteUser() {

    }


    @Test
    void testDeleteUser_whenUserNotExists_ShouldThrowUserNotFoundException() {
        BDDMockito.when(userRepository.existsById(any()))
                .thenReturn(false);

    }
}