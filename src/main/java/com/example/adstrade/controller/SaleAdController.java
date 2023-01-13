package com.example.adstrade.controller;
import com.example.adstrade.model.SaleAd;
import com.example.adstrade.model.dto.*;
import com.example.adstrade.model.requests.CloseSaleAdRequest;
import com.example.adstrade.model.requests.VoteAdRequest;
import com.example.adstrade.service.SaleAdService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.dto.SaleAdDto.convertEntityToDto;
import static java.util.stream.Collectors.toList;

@RestController
@Transactional
@RequestMapping("/sale_ad")
public class SaleAdController {

    private final SaleAdService saleAdService;

    @Autowired
    public SaleAdController(SaleAdService saleAdService) {
        this.saleAdService = saleAdService;

    }

    @ApiOperation(value = "Add a new Sale Ad", notes = "Users can search anything through sale ads.")
    @PostMapping("/save")
    public ResponseEntity<SaleAdDto> saveSaleAd(@RequestBody SaleAdDto saleAdDto){
        return ResponseEntity.of(Optional.of(saleAdService.saveSaleAd(saleAdDto)));
    }

    @ApiOperation(value = "Find a Sale Ad by ID", notes = "The sale ad must exist.")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<SaleAdDto> findSaleAdById(@PathVariable("id") Long id){
        Optional<SaleAd> optionalSaleAd = saleAdService.findSaleAdById(id);
        if(optionalSaleAd.isPresent()){
            SaleAdDto saleAdDto = convertEntityToDto(optionalSaleAd.get());
            return new ResponseEntity<>(saleAdDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //ASTA NU MERGE PT CA NU AVEM FUNCT findSaleAdByIdDto

//    @GetMapping("/get-by-id/{id}")
//    public ResponseEntity<SaleAdDto> findSaleAdById(@PathVariable("id") Long id){
//        SaleAdDto result = saleAdService.findSaleAdByIdDto(id);
//        if(result != null){
//            return new ResponseEntity<>(result, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

    @ApiOperation(value = "Find all Sale Ads by popularity", notes = "Display all existing sale ads by popularity.")
    @GetMapping("/get-by-popularity")
    public List<SaleAdDto> findAllByPopularity(){
        List<SaleAd> optionalSaleAds = saleAdService.findAll();
        optionalSaleAds.sort(new SaleAd());
        if(!optionalSaleAds.isEmpty()){
            return optionalSaleAds.stream().map(SaleAdDto::convertEntityToDto).collect(toList());
        }
        return new ArrayList<>();
    }

    @ApiOperation(value = "Update a Sale Ad", notes = "The sale ad must exist.")
    @PutMapping("/update/{id}")
    public ResponseEntity<SaleAdDto> updateSaleAd(@PathVariable("id") Long id, @RequestBody SaleAdDto dto){
        SaleAdDto result = saleAdService.updateSaleAd(id, dto);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Delete a Sale Ad", notes = "The sale ad must exist.")
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> deleteSaleAdById(@PathVariable("id") Long id){
        boolean result = saleAdService.deleteSaleAd(id);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Find all Sale Ads for a Purchase Ad", notes = "Display all existing sale ads for a specific purchase Ad.")
    @GetMapping("/get-all-sale-ads-for-purchase-ad/{saleAdId}")
    public ResponseEntity<List<SaleAdDto>> getAllSalesForPurchase(@PathVariable("saleAdId") Long saleAdId) {
        List<SaleAdDto> result = saleAdService.getAllSalesForPurchase(saleAdId);
        if(result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ApiOperation(value = "Change Sale Ad status", notes = "A user can decide to change the sale ad status (open/close).")
    @PatchMapping("/change-sale-ad-status/{id}")
    public ResponseEntity<SaleAdDto> changeSaleAdStatus(@PathVariable("id") Long id){
        SaleAdDto result = saleAdService.changeSaleAdStatus(id);
        if(result != null){
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ApiOperation(value = "Close a Sale Ad", notes = "A user can decide to close his sale ad.")
    @PutMapping("/close-sale-ad")
    public ResponseEntity<SaleAdDto> closeSaleAd(@RequestBody CloseSaleAdRequest closeSaleAd){
        SaleAdDto result = saleAdService.closeSaleAd(closeSaleAd.getSaleAdId(), closeSaleAd.getUserId());
        if(result == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ApiOperation(value = "Vote a Sale Ad", notes = "A user can vote a sale ad with a like (option 1) or a dislike (option 0).")
    @PutMapping("/vote-sale-ad")
    public ResponseEntity<SaleAdDto> voteSaleAd(@RequestBody VoteAdRequest request){
        SaleAdDto result = saleAdService.voteSaleAd(request);
        if(result == null){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
