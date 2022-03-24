package com.testingstudy.testingstudy.user;

import com.testingstudy.testingstudy.user.exception.BadRequestException;
import com.testingstudy.testingstudy.user.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User userDto) {
        Boolean existsEmail = userRepository
                .selectExistsEmail(userDto.getEmail());
        if (existsEmail) {
            throw new BadRequestException(
                    "Email " + userDto.getEmail() + " taken");
        }
        userRepository.save(userDto);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(
                    "Student with id " + userId + " does not exists");
        }
        System.err.println("baskocum");
        userRepository.deleteById(userId);
    }
}
