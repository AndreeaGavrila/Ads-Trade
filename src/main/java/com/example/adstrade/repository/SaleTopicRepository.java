package com.example.adstrade.repository;

import com.example.adstrade.enums.TopicValue;
import com.example.adstrade.model.Sale_Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SaleTopicRepository extends JpaRepository<Sale_Topic, Long> {

    Optional<Sale_Topic> findByName(TopicValue topicName);
}
