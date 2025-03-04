package com.example.zolve.repositories;

import com.example.zolve.entities.Subscription;
import com.example.zolve.enums.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

}
