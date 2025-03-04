package com.example.zolve.services;

import com.example.zolve.entities.User;
import com.example.zolve.entities.UserSubscription;
import com.example.zolve.repositories.UserRepository;
import com.example.zolve.repositories.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;


    public boolean userExit(User user) {
        return userRepository.findByEmail(user.getEmail()).isPresent();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<UserSubscription> getCurrentActivePlan(Long userId) {
        return userSubscriptionRepository
                .findByUserIdAndStatus(userId, "ACTIVE")
                .stream().findFirst();
    }
}