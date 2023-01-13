package com.example.adstrade.service;

import com.example.adstrade.model.*;
import com.example.adstrade.model.dto.SaleAdDto;
import com.example.adstrade.model.dto.NotificationDto;
import com.example.adstrade.model.dto.PurchaseAdDto;
import com.example.adstrade.model.requests.VoteAdRequest;
import com.example.adstrade.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.example.adstrade.model.SaleAdMock.saleAd;
import static com.example.adstrade.model.NotificationMock.notification;
import static com.example.adstrade.model.Sale_TopicMock.topic;
import static com.example.adstrade.model.PurchaseAdMock.purchaseAd;
import static com.example.adstrade.model.UserMock.user;
import static com.example.adstrade.model.dto.PurchaseAdDtoMock.purchaseAdDto;
import static com.example.adstrade.model.dto.SaleAdDtoMock.saleAdDto;
import static com.example.adstrade.model.requests.VoteAdRequestMock.voteAdRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SaleAdServiceTest {
    @Mock
    private final SaleAdRepository saleAdRepository;
    @Mock
    private final UserService userService;
    @Mock
    private final PurchaseAdService purchaseAdService;
    @Mock
    private final PurchaseAdRepository purchaseAdRepository;
    @Mock
    private final SaleTopicRepository saleTopicRepository;
    @Mock
    private final BadgeRepository badgeRepository;
    @Mock
    private final NotificationRepository notificationRepository;
    @InjectMocks
    private SaleAdService saleAdService;

    public SaleAdServiceTest(SaleAdRepository saleAdRepository, UserService userService, PurchaseAdService purchaseAdService, PurchaseAdRepository purchaseAdRepository, SaleTopicRepository saleTopicRepository, BadgeRepository badgeRepository, NotificationRepository notificationRepository, SaleAdService saleAdService) {
        this.saleAdRepository = saleAdRepository;
        this.userService = userService;
        this.purchaseAdService = purchaseAdService;
        this.purchaseAdRepository = purchaseAdRepository;
        this.saleTopicRepository = saleTopicRepository;
        this.badgeRepository = badgeRepository;
        this.notificationRepository = notificationRepository;
        this.saleAdService = saleAdService;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findSaleAdById() {
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        Optional<SaleAd> result = saleAdService.findSaleAdById(1L);
        assertEquals(1, result.get().getSale_id());
    }

    @Test
    void findSaleAdByIdDto() {
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        SaleAdDto result = saleAdService.findSaleAdByIdDto(1L);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void findAllSaleAds(){
        List<SaleAd> saleAds = new ArrayList<>();
        saleAds.add(saleAd());
        when(saleAdService.findAll()).thenReturn(saleAds);
        assertEquals(1, saleAds.size());
    }

    @Test
    void saveSaleAdOptionalUserTrue(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        SaleAdDto result = saleAdService.saveSaleAd(saleAdDto());
        assertNotNull(result);
    }

    @Test
    void saveSaleAdOptionalUserFalse(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.empty());
        SaleAdDto dto = saleAdService.saveSaleAd(saleAdDto());
        assertNull(dto);
    }

    @Test
    void saveSaleAdOptionalTopicTrue(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleTopicRepository.findById(anyLong())).thenReturn(Optional.of(Sale_TopicMock.topic()));
        when(saleAdRepository.save(any())).thenReturn(purchaseAd());
        assertNotNull(saleAdService.saveSaleAd(saleAdDto()));
    }

    @Test
    void saveSaleAdOptionalTopicFalse(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleTopicRepository.findByName(any())).thenReturn(Optional.empty());
        when(saleAdRepository.save(any())).thenReturn(purchaseAd());
        assertNotNull(saleAdService.saveSaleAd(saleAdDto()));
    }

    @Test
    void saveSaleAdNull(){
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        SaleAdDto result = saleAdService.saveSaleAd(saleAdDto());
        assertNull(result);
    }

    @Test
    void saveSaleAdNotNull(){
        when(saleTopicRepository.findById(anyLong())).thenReturn(Optional.of(Sale_TopicMock.topic()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        SaleAdDto result = saleAdService.saveSaleAd(saleAdDto());
        assertNotNull(result);
    }

    @Test
    void updateSaleAdPresent(){
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        SaleAdDto result = saleAdService.updateSaleAd(1L, saleAdDto());
        assertNotNull(result);
    }

    @Test
    void updateSaleAd(){
        when(saleAdService.findSaleAdById(anyLong())).thenReturn(Optional.empty());
        SaleAdDto result = saleAdService.updateSaleAd(saleAd().getSale_id(), saleAdDto());
        assertNull(result);
    }

    @Test
    void deleteSaleAdAdTrue(){
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        assertTrue(saleAdService.deleteSaleAd(1L));
    }

    @Test
    void deleteSaleAdAdFalse(){
        boolean result = saleAdService.deleteSaleAd(null);
        assertFalse(result);
    }

    @Test
    void deleteSaleAdAdTopic(){
        Purchase_Topic topic = Purchase_TopicMock.topic();
        saleAdService.deleteSaleTopic(topic.getId());
        verify(saleTopicRepository).deleteById(1L);
    }

    // getAllSalesForPurchase

    @Test
    void getAllSalesForPurchaseNotEmpty() {
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        assertEquals(1, saleAdService.getAllSalesForPurchase(1L).size());
    }

    @Test
    void getAllSalesForPurchaseEmpty() {
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertEquals(0, saleAdService.getAllSalesForPurchase(1L).size());
    }

    // closeSaleAd

    @Test
    void closeSaleAdPurchaseNotAccepted(){
        SaleAd saleAd = saleAd();
        PurchaseAd purchaseAd = purchaseAd();
        purchaseAd.setCompleted(true);
        purchaseAd.setSaleAds(Collections.singletonList(saleAd));
        when(saleAdService.findSaleAdById((anyLong()))).thenReturn(Optional.of(saleAd));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        SaleAdDto result = saleAdService.closeSaleAd(1L, 1L);
        assertNull(result);
    }

    @Test
    void closeSaleAdUserIdsNotMatching(){
        User user = user();
        user.setId(2L);
        when(saleAdService.findSaleAdById((anyLong()))).thenReturn(Optional.of(saleAd()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user));
        SaleAdDto result = saleAdService.closeSaleAd(1L, 1L);
        assertNull(result);
    }

    @Test
    void closeSaleAdMatch(){
        when(saleAdRepository.findById((anyLong()))).thenReturn(Optional.of(saleAd()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        SaleAdDto result = saleAdService.closeSaleAd(1L, 1L);
        assertNotNull(result);
    }

    // voteSaleAd

    @Test
    void testVoteSaleAdWithNonExistingSaleAd() {
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertNull(saleAdService.voteSaleAd(new VoteAdRequest()));
    }

    @Test
    void testVoteSaleAdWhenUserAlreadyVoted(){
        User user = user();
        SaleAd saleAd = saleAd();
        user.addVotedSaleAd(saleAd);
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user));
        assertNull(saleAdService.voteSaleAd(voteAdRequest()));
    }

    @Test
    void testVoteSaleAdOK(){
        User user = user();
        user.setVotedSaleAds(new HashSet<>());
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        assertNotNull(saleAdService.voteSaleAd(voteAdRequest()));
    }

}
