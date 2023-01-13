package com.example.adstrade.service;

import com.example.adstrade.model.Purchase_Topic;
import com.example.adstrade.model.Sale_Topic;
import com.example.adstrade.model.dto.PurchaseTopicDto;
import com.example.adstrade.model.dto.SaleTopicDto;
import com.example.adstrade.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class SaleTopicService {

    private final SaleTopicRepository saleTopicRepository;

    @Autowired
    public SaleTopicService(SaleTopicRepository saleTopicRepository) {
        this.saleTopicRepository = saleTopicRepository;
    }

    @Transactional
    public Optional<Sale_Topic> findTopicById(Long id) {
        return saleTopicRepository.findById(id);
    }

    @Transactional
    public boolean deleteSaleTopic(Long topicId){
        Optional<Sale_Topic> optionalTopic = findTopicById(topicId);
        if(optionalTopic.isPresent()){
            saleTopicRepository.delete(optionalTopic.get());
            return true;
        }
        return false;
    }

    @Transactional
    public List<SaleTopicDto> getAllTopics() {
        List<Sale_Topic> allTopics = saleTopicRepository.findAll();
        return allTopics.stream().map(SaleTopicDto::convertEntityToDto).collect(toList());
    }

    @Transactional
    public List<Sale_Topic> findAll(){
        return saleTopicRepository.findAll();
    }
}
