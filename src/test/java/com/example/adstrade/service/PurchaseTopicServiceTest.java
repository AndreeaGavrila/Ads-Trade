package com.example.adstrade.service;

import com.example.adstrade.model.Purchase_Topic;
import com.example.adstrade.repository.PurchaseTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.Purchase_TopicMock.topic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class PurchaseTopicServiceTest {

    @Mock
    private final PurchaseTopicRepository purchaseTopicRepository;

    @InjectMocks
    private PurchaseTopicService purchaseTopicService;

    public PurchaseTopicServiceTest(PurchaseTopicRepository purchaseTopicRepository, PurchaseTopicService purchaseTopicService) {
        this.purchaseTopicRepository = purchaseTopicRepository;
        this.purchaseTopicService = purchaseTopicService;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findTopicById(){
        when(purchaseTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        Optional<Purchase_Topic> result = purchaseTopicService.findTopicById(1L);
        assertEquals(1, result.get().getId());
    }

    @Test
    void deleteTrue(){
        when(purchaseTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        assertTrue(purchaseTopicService.deletePurchaseTopic(1L));
    }

    @Test
    void deleteFalse(){
        when(purchaseTopicRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(purchaseTopicService.deletePurchaseTopic(1L));
    }

    @Test
    void getAllTopics(){
        List<Purchase_Topic> topics = new ArrayList<>();
        topics.add(topic());
        when(purchaseTopicService.findAll()).thenReturn(topics);
        assertEquals(1, topics.size());
    }

}
