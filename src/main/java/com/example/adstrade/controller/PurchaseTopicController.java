package com.example.adstrade.controller;

import com.example.adstrade.model.Purchase_Topic;
import com.example.adstrade.model.dto.PurchaseTopicDto;
import com.example.adstrade.service.PurchaseTopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.dto.PurchaseTopicDto.convertEntityToDto;

@RestController
@RequestMapping("/purchase_topic")
public class PurchaseTopicController {

    private final PurchaseTopicService purchaseTopicService;

    @Autowired
    public PurchaseTopicController(PurchaseTopicService purchaseTopicService) {
        this.purchaseTopicService = purchaseTopicService;
    }

    @ApiOperation(value = "Delete a Purchase Topic", notes = "The topic must exist.")
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deletePurchaseTopicById(@PathVariable("id") Long id){
        boolean result = purchaseTopicService.deletePurchaseTopic(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find a Purchase Topic by ID", notes = "The topic must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PurchaseTopicDto> findTopicById(@PathVariable("id") Long id){
        Optional<Purchase_Topic> optionalTopic = purchaseTopicService.findTopicById(id);
        if(optionalTopic.isPresent()){
            PurchaseTopicDto topicDto = convertEntityToDto(optionalTopic.get());
            return new ResponseEntity<>(topicDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @ApiOperation(value = "Find all Purchase Topics", notes = "Display all existing topics.")
    @GetMapping("/get-all-topics")
    public ResponseEntity<List<PurchaseTopicDto>> getAllTopics() {
        return new ResponseEntity<>(purchaseTopicService.getAllTopics(), HttpStatus.OK);
    }

}
