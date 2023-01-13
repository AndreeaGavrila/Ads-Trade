package com.example.adstrade.controller;

import com.example.adstrade.model.Sale_Topic;
import com.example.adstrade.model.dto.SaleTopicDto;
import com.example.adstrade.service.SaleTopicService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.dto.SaleTopicDto.convertEntityToDto;

@RestController
@RequestMapping("/sale_topic")
public class SaleTopicController {

    private final SaleTopicService saleTopicService;

    @Autowired
    public SaleTopicController(SaleTopicService saleTopicService) {
        this.saleTopicService = saleTopicService;
    }

    @ApiOperation(value = "Delete a Sale Topic", notes = "The topic must exist.")
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteSaleTopicById(@PathVariable("id") Long id){
        boolean result = saleTopicService.deleteSaleTopic(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find a Sale Topic by ID", notes = "The topic must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<SaleTopicDto> findTopicById(@PathVariable("id") Long id){
        Optional<Sale_Topic> optionalTopic = saleTopicService.findTopicById(id);
        if(optionalTopic.isPresent()){
            SaleTopicDto topicDto = convertEntityToDto(optionalTopic.get());
            return new ResponseEntity<>(topicDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @ApiOperation(value = "Find all Sale Topics", notes = "Display all existing topics.")
    @GetMapping("/get-all-topics")
    public ResponseEntity<List<SaleTopicDto>> getAllTopics() {
        return new ResponseEntity<>(saleTopicService.getAllTopics(), HttpStatus.OK);
    }

}
