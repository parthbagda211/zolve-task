package com.example.zolve.services;


import com.example.zolve.entities.Subscription;
import com.example.zolve.entities.User;
import com.example.zolve.entities.UserSubscription;
import com.example.zolve.repositories.SubscriptionRepository;
import com.example.zolve.repositories.UserRepository;
import com.example.zolve.repositories.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserSubscriptionService {

    @Autowired
    private  UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private  SubscriptionRepository subscriptionRepository;

    @Transactional
    public UserSubscription subscribeUser(Long userId, Long subscriptionId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));


        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription plan not found"));


        UserSubscription newSubscription = new UserSubscription();
        newSubscription.setUser(user);
        newSubscription.setSubscription(subscription);
        newSubscription.setStartDate(LocalDateTime.now());
        newSubscription.setEndDate(LocalDateTime.now().plusDays(subscription.getDuration()));
        newSubscription.setStatus("ACTIVE");

        return userSubscriptionRepository.save(newSubscription);
    }


    public List<UserSubscription> getPastSubscriptions(Long userId) {
        return userSubscriptionRepository.findByUserIdAndStatus(userId, "EXPIRED");
    }

    public void unsubscribeUser(Long userId) {
        userSubscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE")
                .stream()
                .findFirst()
                .ifPresent(sub -> {
                    sub.setStatus("CANCELLED");
                    userSubscriptionRepository.save(sub);
                });
    }

    public boolean hasActiveSubscription(Long userId) {
        return userSubscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE")
                .stream()
                .anyMatch(subscription -> subscription.getStatus().equals("ACTIVE"));
    }

    public UserSubscription upgradeSubscription(Long userId, Long newSubscriptionId) {

        if (!hasActiveSubscription(userId)) {
            throw new IllegalStateException("User has no active subscription to upgrade");
        }


        Optional<UserSubscription> currentSubscriptionOpt = userSubscriptionRepository.findByUserIdAndStatus(userId, "ACTIVE").stream().findFirst();
        UserSubscription currentSubscription = currentSubscriptionOpt.orElseThrow(() -> new IllegalStateException("User has no active subscription to upgrade"));


        Subscription newSubscription = subscriptionRepository.findById(newSubscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("New subscription plan not found"));


        currentSubscription.setStatus("EXPIRED");
        currentSubscription.setEndDate(LocalDateTime.now());
        userSubscriptionRepository.save(currentSubscription);


        UserSubscription upgradedSubscription = new UserSubscription();
        upgradedSubscription.setUser(currentSubscription.getUser());
        upgradedSubscription.setSubscription(newSubscription);
        upgradedSubscription.setStartDate(LocalDateTime.now());
        upgradedSubscription.setEndDate(LocalDateTime.now().plusDays(newSubscription.getDuration()));
        upgradedSubscription.setStatus("ACTIVE");

        return userSubscriptionRepository.save(upgradedSubscription);
    }

}

