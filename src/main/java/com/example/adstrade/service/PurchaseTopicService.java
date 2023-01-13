package com.example.adstrade.service;

import com.example.adstrade.model.dto.PurchaseTopicDto;
import com.example.adstrade.repository.*;
import com.example.adstrade.model.Purchase_Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class PurchaseTopicService {

    private final PurchaseTopicRepository purchaseTopicRepository;

    @Autowired
    public PurchaseTopicService(PurchaseTopicRepository purchaseTopicRepository) {
        this.purchaseTopicRepository = purchaseTopicRepository;
    }

    @Transactional
    public Optional<Purchase_Topic> findTopicById(Long id) {
        return purchaseTopicRepository.findById(id);
    }

    @Transactional
    public boolean deletePurchaseTopic(Long topicId){
        Optional<Purchase_Topic> optionalTopic = findTopicById(topicId);
        if(optionalTopic.isPresent()){
            purchaseTopicRepository.delete(optionalTopic.get());
            return true;
        }
        return false;
    }

    @Transactional
    public List<PurchaseTopicDto> getAllTopics() {
        List<Purchase_Topic> allTopics = purchaseTopicRepository.findAll();
        return allTopics.stream().map(PurchaseTopicDto::convertEntityToDto).collect(toList());
    }

    @Transactional
    public List<Purchase_Topic> findAll(){
        return purchaseTopicRepository.findAll();
    }

}
