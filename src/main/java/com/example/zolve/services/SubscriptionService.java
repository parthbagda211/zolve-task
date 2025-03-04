package com.example.zolve.services;

import com.example.zolve.entities.Subscription;
import com.example.zolve.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    private  SubscriptionRepository subscriptionRepository;


    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public boolean subscriptionExist(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId).isEmpty();
    }

}