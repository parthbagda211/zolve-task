package com.example.zolve.controllers;

import com.example.zolve.entities.UserSubscription;
import com.example.zolve.services.SubscriptionService;
import com.example.zolve.services.UserService;
import com.example.zolve.services.UserSubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscription")
public class UserSubscriptionController {

    @Autowired
    private UserSubscriptionService service;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;


    @PostMapping("/{userId}/subscribe/{subscriptionId}")
    public Object subscribe(@PathVariable Long userId, @PathVariable Long subscriptionId) {

        if (userService.getUserById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (subscriptionService.subscriptionExist(subscriptionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subscription Plan not found");
        }

        if (service.hasActiveSubscription(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already has an active subscription");
        }


        return ResponseEntity.ok(service.subscribeUser(userId, subscriptionId));
    }


    @PutMapping("/{userId}/upgrade/{newSubscriptionId}")
    public ResponseEntity<?> upgradePlan(@PathVariable Long userId, @PathVariable Long newSubscriptionId) {

        if (userService.getUserById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (subscriptionService.subscriptionExist(newSubscriptionId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Upgrade plan not found");
        }

        if (!service.hasActiveSubscription(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have an active plan to upgrade");
        }

        UserSubscription upgradedSubscription = service.upgradeSubscription(userId, newSubscriptionId);
        return ResponseEntity.ok(upgradedSubscription);
    }

    @DeleteMapping("/{userId}/unsubscribe")
    public ResponseEntity<?> unsubscribe(@PathVariable Long userId) {

        if (userService.getUserById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        if (!service.hasActiveSubscription(userId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not have an active plan to unsubscribe");
        }
        service.unsubscribeUser(userId);
        return ResponseEntity.ok("User unsubscribed successfully");
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<?> pastSubscriptions(@PathVariable Long userId) {
        if (userService.getUserById(userId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(service.getPastSubscriptions(userId));
    }


}