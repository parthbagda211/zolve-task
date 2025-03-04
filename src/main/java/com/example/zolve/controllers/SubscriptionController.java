package com.example.zolve.controllers;

import com.example.zolve.entities.Subscription;
import com.example.zolve.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/subscriptions")
    List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }
}
