package com.example.zolve.controllers;

import com.example.zolve.entities.User;
import com.example.zolve.repositories.UserSubscriptionRepository;
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

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userService.userExit(user)) {
            return ResponseEntity.badRequest().body("User already exists");
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping
    public Object getAllUsers() {

        List<User> users = userService.getAllUsers();
        if (users == null || users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No users found");
        }
        return ResponseEntity.ok(users);

    }

    @GetMapping("/{id}")
    public Object getUserById(@PathVariable Long id) {
        if (userService.getUserById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    public Object deleteUser(@PathVariable Long id) {
        if (userService.getUserById(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.deleteUser(id);
       return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/activeplan/{userId}")
    public ResponseEntity<?> getCurrentActivePlan(@PathVariable Long userId) {
        if (userService.getCurrentActivePlan(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User does not have an active plan");
        }
        return ResponseEntity.ok(userService.getCurrentActivePlan(userId));
    }
}