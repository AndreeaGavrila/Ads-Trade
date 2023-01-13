package com.example.adstrade.service;

import com.example.adstrade.model.Sale_Topic;
import com.example.adstrade.repository.SaleTopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.Sale_TopicMock.topic;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class SaleTopicServiceTest {

    @Mock
    private final SaleTopicRepository saleTopicRepository;

    @InjectMocks
    private SaleTopicService saleTopicService;

    public SaleTopicServiceTest(SaleTopicRepository saleTopicRepository, SaleTopicService saleTopicService) {
        this.saleTopicRepository = saleTopicRepository;
        this.saleTopicService = saleTopicService;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findTopicById(){
        when(saleTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        Optional<Sale_Topic> result = saleTopicService.findTopicById(1L);
        assertEquals(1, result.get().getId());
    }

    @Test
    void deleteTrue(){
        when(saleTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        assertTrue(saleTopicService.deleteSaleTopic(1L));
    }

    @Test
    void deleteFalse(){
        when(saleTopicRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertFalse(saleTopicService.deleteSaleTopic(1L));
    }

    @Test
    void getAllTopics(){
        List<Sale_Topic> topics = new ArrayList<>();
        topics.add(topic());
        when(saleTopicService.findAll()).thenReturn(topics);
        assertEquals(1, topics.size());
    }
}
