package com.example.zolve.repositories;

import com.example.zolve.entities.UserSubscription;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    List<UserSubscription> findByUserIdAndStatus(Long userId, String status);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserSubscription us WHERE us.user.id = :userId")
    void deleteByUserId(Long userId);
}