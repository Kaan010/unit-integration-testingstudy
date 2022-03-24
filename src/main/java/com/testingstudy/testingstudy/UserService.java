package com.testingstudy.testingstudy;

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
        return new ArrayList<>(userRepository.findAll());
    }

    public void createUser(User userDto) {
        User newHiker = new User(
                userDto.getFirstName(),
                userDto.getEmail(),
                userDto.getGender()
        );
        userRepository.save(newHiker);
    }

}
