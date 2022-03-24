package com.testingstudy.testingstudy;


import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
                return userService.getAllUsers();
    }

    @PostMapping()
    public void addUser(@Valid @RequestBody User userRequest) {
                userService.createUser(userRequest);
    }

    @DeleteMapping
    public void deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
    }


}
