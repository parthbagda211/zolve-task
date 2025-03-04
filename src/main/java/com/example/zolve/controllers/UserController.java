package com.example.zolve.controllers;

import com.example.zolve.entities.User;
import com.example.zolve.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.userExit(user)) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/activeplan/{userId}")
    public ResponseEntity<?> getCurrentActivePlan(@PathVariable Long userId) {
        if (userService.getCurrentActivePlan(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have an active plan");
        }
        return ResponseEntity.ok(userService.getCurrentActivePlan(userId));
    }
}