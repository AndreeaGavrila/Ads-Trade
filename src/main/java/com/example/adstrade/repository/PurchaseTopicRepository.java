package com.example.adstrade.repository;

import com.example.adstrade.enums.TopicValue;
import com.example.adstrade.model.Purchase_Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseTopicRepository extends JpaRepository<Purchase_Topic, Long> {

    Optional<Purchase_Topic> findByName(TopicValue topicName);
}

