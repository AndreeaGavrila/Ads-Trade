package com.example.adstrade.controller;

import com.example.adstrade.enums.NotificationStatus;
import com.example.adstrade.model.dto.NotificationDto;
import com.example.adstrade.model.dto.PurchaseAdDto;
import com.example.adstrade.model.requests.MarkCompleteAdRequest;
import com.example.adstrade.service.PurchaseAdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static com.example.adstrade.model.dto.PurchaseAdDtoMock.purchaseAdDto;
import static com.example.adstrade.model.dto.SaleAdDtoMock.saleAdDto;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(PurchaseAdController.class)
public class PurchaseAdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PurchaseAdService purchaseAdService;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }


    @SneakyThrows
    @Test
    void testSavePurchaseAds() {
        PurchaseAdDto dto = purchaseAdDto();
        when(purchaseAdService.savePurchaseAd(any())).thenReturn(dto);
        mockMvc.perform(post("/purchase_ad/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("food"));
    }

    @SneakyThrows
    @Test
    void testFindPurchaseAdByIdOK() {
        when(purchaseAdService.findPurchaseAdByIdDto(anyLong())).thenReturn(purchaseAdDto());
        mockMvc.perform(get("/purchase_ad/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @SneakyThrows
    @Test
    void testFindPurchaseAdByIdNotOK() {
        when(purchaseAdService.findPurchaseAdByIdDto(anyLong())).thenReturn(null);
        mockMvc.perform(get("/purchase_ad/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void testUpdatePurchaseAdOK() {
        PurchaseAdDto dto = purchaseAdDto();
        when(purchaseAdService.updatePurchaseAd(anyLong(), any())).thenReturn(dto);
        mockMvc.perform(put("/purchase_ad/update/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @SneakyThrows
    @Test
    void testUpdatePurchaseAdNotOK() {
        when(purchaseAdService.updatePurchaseAd(anyLong(), any())).thenReturn(null);
        mockMvc.perform(put("/purchase_ad/update/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(purchaseAdDto())))
                .andExpect(status().isNotFound());
    }
    
    @SneakyThrows
    @Test
    void testDeletePurchaseAdByIdOK() {
        when(purchaseAdService.deletePurchaseAd(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/purchase_ad/delete-by-id/{id}", 1L))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testDeletePurchaseAdByIdNotOK() {
        when(purchaseAdService.deletePurchaseAd(anyLong())).thenReturn(false);
        mockMvc.perform(delete("/purchase_ad/delete-by-id/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @SneakyThrows
    @Test
    void testSeeNotificationOK() {
        NotificationDto dto = new NotificationDto("type", NotificationStatus.UNSEEN, 1L);
        when(purchaseAdService.seeNotification(anyLong())).thenReturn(dto);
        mockMvc.perform(patch("/purchase_ad/see-notification/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseAdId").value(1L));
    }

    @SneakyThrows
    @Test
    void testSeeNotificationNotOK() {
        when(purchaseAdService.seeNotification(anyLong())).thenReturn(null);
        mockMvc.perform(patch("/purchase_ad/see-notification/{id}", 1L))
                .andExpect(status().isNotFound());
    }
    
    @Test
    @SneakyThrows
    void testMarkCompleteAdOK() {
        MarkCompleteAdRequest request = new MarkCompleteAdRequest();
        request.setPurchaseAdId(1L);
        request.setPurchaseAdId(1L);
        request.setUserId(1L);
        when(purchaseAdService.markCompleteAd(anyLong(), anyLong(), anyLong())).thenReturn(purchaseAdDto());
        mockMvc.perform(put("/purchase_ad/mark-complete-ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("food"));
    }

    @Test
    @SneakyThrows
    void testMarkCompleteAdNotOK() {
        when(purchaseAdService.markCompleteAd(anyLong(), anyLong(), anyLong())).thenReturn(null);
        mockMvc.perform(put("/purchase_ad/mark-complete-ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new MarkCompleteAdRequest())))
                .andExpect(status().isNotFound());
    }
    
    @SneakyThrows
    @Test
    void testSortSaleAdsByOptionNotOK() {
        when(purchaseAdService.sortSaleAdsByOption(anyLong(), anyString(), anyString())).thenReturn(null);
        mockMvc.perform(get("/purchase_ad/{id}/sort-sale-ads-order-by/{option}/{type}", 1L, "date", "asc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void testSortSaleAdsByOptionOK() {
        when(purchaseAdService.sortSaleAdsByOption(anyLong(), anyString(), anyString())).thenReturn(purchaseAdDto());
        mockMvc.perform(get("/purchase_ad/{id}/sort-sale-ads-order-by/{option}/{type}", 1L, "date", "asc")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.purchaseAds", hasSize(1)));
    }
    
    @SneakyThrows
    @Test
    void testFilterSaleAdsByDateFails() {
        when(purchaseAdService.filterSaleAdsByDate(anyString())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/purchase_ad/filter-sale-ads-by-date/{criteria}", "c")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @SneakyThrows
    @Test
    void testFilterSaleAdsByDateSuccess() {
        when(purchaseAdService.filterSaleAdsByDate(anyString())).thenReturn(singletonList(saleAdDto()));
        mockMvc.perform(get("/purchase_ad/filter-sale-ads-by-date/{criteria}", "week")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
