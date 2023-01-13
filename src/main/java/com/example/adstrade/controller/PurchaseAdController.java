package com.example.adstrade.controller;

import com.example.adstrade.model.*;
import com.example.adstrade.model.dto.*;
import com.example.adstrade.model.requests.MarkCompleteAdRequest;
import com.example.adstrade.service.PurchaseAdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
@Transactional
@RequestMapping("/purchase_ad")
public class PurchaseAdController {

    private final PurchaseAdService purchaseAdService;

    @Autowired
    public PurchaseAdController(PurchaseAdService purchaseAdService) {
        this.purchaseAdService = purchaseAdService;
    }

    @ApiOperation(value = "Add a new Purchase Ad", notes = "Users can search anything through purchase ads.")
    @PostMapping("/save")
    public ResponseEntity<PurchaseAdDto> savePurchaseAds(@RequestBody PurchaseAdDto purchaseAdDto){
        return ResponseEntity.of(Optional.of(purchaseAdService.savePurchaseAd(purchaseAdDto)));
    }

//    @GetMapping("/get-by-id/{id}")
//    public ResponseEntity<PurchaseAdDto> findPurchaseAdById(@PathVariable("id") Long id){
//        Optional<PurchaseAd> optionalPurchaseAd = purchaseAdService.findPurchaseAdById(id);
//        if(optionalPurchaseAd.isPresent()){
//            PurchaseAdDto purchaseAdDto = PurchaseAdDto.convertEntityToDto(optionalPurchaseAd.get());
//            return new ResponseEntity<>(purchaseAdDto, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    //ASTA ERA INAINTE

    @ApiOperation(value = "Find a Purchase Ad by ID", notes = "The purchase ad must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<PurchaseAdDto> findPurchaseAdById(@PathVariable("id") Long id){
        PurchaseAdDto result = purchaseAdService.findPurchaseAdByIdDto(id);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find all Purchase Ads by popularity", notes = "Display all existing purchase ads by popularity.")
    @GetMapping("/get-by-popularity")
    public List<PurchaseAdDto> findAllByPopularity(){
        List<PurchaseAd> optionalPurchaseAds = purchaseAdService.findAll();
        optionalPurchaseAds.sort(new PurchaseAd());
        if(!optionalPurchaseAds.isEmpty()){
            return optionalPurchaseAds.stream().map(PurchaseAdDto::convertEntityToDto).collect(toList());
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "Update a Purchase Ad", notes = "The purchase ad must exist.")
    @PutMapping("/update/{id}")
    public ResponseEntity<PurchaseAdDto> updatePurchaseAd(@PathVariable("id") Long id, @RequestBody PurchaseAdDto dto){
        PurchaseAdDto result = purchaseAdService.updatePurchaseAd(id, dto);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Purchase Ad", notes = "The purchase ad must exist.")
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deletePurchaseAdById(@PathVariable("id") Long id){
        boolean result = purchaseAdService.deletePurchaseAd(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @ApiOperation(value = "See a notification", notes = "See a received notification by ID.")
    @PatchMapping("/see-notification/{id}")
    public ResponseEntity<NotificationDto> seeNotification(@PathVariable("id") Long id){
        NotificationDto result = purchaseAdService.seeNotification(id);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiOperation(value = "Mark as completed a Purchase Ad", notes = "After a user found a suitable sell offer, he can mark as completed his purchase ad.")
    @PutMapping("/mark-complete-ad")
    public ResponseEntity<PurchaseAdDto> markCompleteAd(@RequestBody MarkCompleteAdRequest markCompleteAdRequest){
        PurchaseAdDto result = purchaseAdService.markCompleteAd( markCompleteAdRequest.getPurchaseAdId(),
                markCompleteAdRequest.getUserId(), markCompleteAdRequest.getSaleAdId());
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    //SORT
    @ApiOperation(value = "Sort all Sale Ads", notes = "Sort all Sale Ads by date/popularity asc/desc.")
    @GetMapping("{id}/sort-sale-ads-order-by/{option}/{type}")
    public ResponseEntity<PurchaseAdDto> orderSaleAds(@PathVariable("id") Long id, @PathVariable("option") String option, @PathVariable String type){
        PurchaseAdDto result = purchaseAdService.sortSaleAdsByOption(id, option, type);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiOperation(value = "Filter all Sale Ads by date", notes = "Filter all Sale Ads by week/month/year.")
    //FILTER
    @GetMapping("/filter-sale-ads-by-date/{criteria}")
    public ResponseEntity<List<SaleAdDto>> filterSaleAdsByDate(@PathVariable("criteria") String criteria){
        List<SaleAdDto> result = purchaseAdService.filterSaleAdsByDate(criteria);
        if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
