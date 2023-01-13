package com.example.adstrade.controller;

import com.example.adstrade.model.dto.SaleAdDto;
import com.example.adstrade.model.requests.CloseSaleAdRequest;
import com.example.adstrade.service.SaleAdService;
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
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.SaleAdMock.saleAd;
import static com.example.adstrade.model.dto.SaleAdDtoMock.saleAdDto;
import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@WebMvcTest(SaleAdController.class)
public class SaleAdControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SaleAdService saleAdService;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void testSaveSaleAds() {
        SaleAdDto dto = saleAdDto();
        when(saleAdService.saveSaleAd(any())).thenReturn(dto);
        mockMvc.perform(post("/sale_ad/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("food"));
    }

    @SneakyThrows
    @Test
    void testFindSaleAdByIdOK() {
        when(saleAdService.findSaleAdByIdDto(anyLong())).thenReturn(saleAdDto());
        mockMvc.perform(get("/sale_ad/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @SneakyThrows
    @Test
    void testFindSaleAdByIdNotOK() {
        when(saleAdService.findSaleAdByIdDto(anyLong())).thenReturn(null);
        mockMvc.perform(get("/sale_ad/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testFindAllByPopularityEmpty() {
        when(saleAdService.findAll()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/sale_ad/get-by-popularity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @SneakyThrows
    void testFindAllByPopularityNotEmpty() {
        when(saleAdService.findAll()).thenReturn(singletonList(saleAd()));
        mockMvc.perform(get("/sale_ad/get-by-popularity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @SneakyThrows
    @Test
    void testUpdateSaleAdOK() {
        SaleAdDto dto = saleAdDto();
        when(saleAdService.updateSaleAd(anyLong(), any())).thenReturn(dto);
        mockMvc.perform(put("/sale_ad/update/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OPEN"));
    }

    @SneakyThrows
    @Test
    void testUpdateSaleAdNotOK() {
        when(saleAdService.updateSaleAd(anyLong(), any())).thenReturn(null);
        mockMvc.perform(put("/sale_ad/update/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(saleAdDto())))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void testDeleteSaleAdByIdOK() {
        when(saleAdService.deleteSaleAd(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/sale_ad/delete-by-id/{id}", 1L))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testDeleteSaleAdByIdNotOK() {
        when(saleAdService.deleteSaleAd(anyLong())).thenReturn(false);
        mockMvc.perform(delete("/sale_ad/delete-by-id/{id}", 1L))
                .andExpect(status().isNotFound());
    }


    @Test
    @SneakyThrows
    void testGetAllAnswersForSaleAdOK() {
        when(saleAdService.getAllSalesForPurchase(anyLong())).thenReturn(singletonList(saleAdDto()));
        mockMvc.perform(get("/sale_ad/get-all-sale-ads-for-purchase-ad/{saleAdId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    @SneakyThrows
    void testGetAllAnswersForSaleAdNotOK() {
        when(saleAdService.getAllSalesForPurchase(anyLong())).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/sale_ad/get-all-sale-ads-for-purchase-ad/{saleAdId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    @SneakyThrows
    void testCloseSaleAdOK() {
        CloseSaleAdRequest request = new CloseSaleAdRequest();
        request.setSaleAdId(1L);
        request.setUserId(1L);
        when(saleAdService.closeSaleAd(anyLong(), anyLong())).thenReturn(saleAdDto());
        mockMvc.perform(put("/sale_ad/close-sale-ad")
                        .content(asJsonString(request))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.topic").value("food"));
    }

    @Test
    @SneakyThrows
    void testCloseSaleAdNotOK() {
        when(saleAdService.closeSaleAd(anyLong(), anyLong())).thenReturn(null);
        mockMvc.perform(put("/sale_ad/close-sale-ad")
                        .content(asJsonString(new CloseSaleAdRequest()))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void testVoteResponseOK() {
        SaleAdDto dto = saleAdDto();
        when(saleAdService.voteSaleAd(any())).thenReturn(dto);
        mockMvc.perform(put("/sale_ad/vote-sale-ad")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.saleAdId").value(1L));
    }

    @SneakyThrows
    @Test
    void testVoteResponseNotOK() {
        when(saleAdService.voteSaleAd(any())).thenReturn(null);
        mockMvc.perform(put("/sale_ad/vote-sale-ad")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(saleAdDto())))
                .andExpect(status().isInternalServerError());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
