package com.example.adstrade.controller;

import com.example.adstrade.service.SaleTopicService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static com.example.adstrade.model.Sale_TopicMock.topic;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


@WebMvcTest(SaleTopicController.class)
public class SaleTopicControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private SaleTopicService saleTopicService;

    @BeforeEach
    void setUp() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @SneakyThrows
    @Test
    void testDeletePurchaseTopicByIdOK() {
        when(saleTopicService.deleteSaleTopic(anyLong())).thenReturn(true);
        mockMvc.perform(delete("/purchase_topic/delete-by-id/{id}", 1L))
                .andExpect(status().isOk());
    }

    @SneakyThrows
    @Test
    void testDeletePurchaseTopicByIdNotOK() {
        when(saleTopicService.deleteSaleTopic(anyLong())).thenReturn(false);
        mockMvc.perform(delete("/purchase_topic/delete-by-id/{id}", 1L))
                .andExpect(status().isNotFound());
    }

    @SneakyThrows
    @Test
    void testFindTopicByIdOK() {
        when(saleTopicService.findTopicById(anyLong())).thenReturn(Optional.of(topic()));
        mockMvc.perform(get("/purchase_topic/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("FOOD"));
    }

    @SneakyThrows
    @Test
    void testFindTopicByIdNotOK() {
        when(saleTopicService.findTopicById(anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(get("/purchase_topic/get-by-id/{id}", 1L)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}

