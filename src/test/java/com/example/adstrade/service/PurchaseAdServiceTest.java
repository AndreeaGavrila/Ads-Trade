package com.example.adstrade.service;

import com.example.adstrade.model.*;
import com.example.adstrade.model.dto.NotificationDto;
import com.example.adstrade.model.dto.PurchaseAdDto;
import com.example.adstrade.model.dto.SaleAdDto;
import com.example.adstrade.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.NotificationMock.notification;
import static com.example.adstrade.model.PurchaseAdMock.purchaseAd;
import static com.example.adstrade.model.Purchase_TopicMock.topic;
import static com.example.adstrade.model.SaleAdMock.saleAd;
import static com.example.adstrade.model.UserMock.user;
import static com.example.adstrade.model.dto.PurchaseAdDtoMock.purchaseAdDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PurchaseAdServiceTest {

    @Mock
    private final PurchaseAdRepository purchaseAdRepository;
    @Mock
    private final UserService userService;
    @Mock
    private final PurchaseTopicRepository purchaseTopicRepository;
    @Mock
    private final SaleAdRepository saleAdRepository;
    @Mock
    private final BadgeRepository badgeRepository;
    @Mock
    private final NotificationRepository notificationRepository;
    @InjectMocks
    private PurchaseAdService purchaseAdService;

    public PurchaseAdServiceTest(PurchaseAdRepository purchaseAdRepository, UserService userService, PurchaseTopicRepository purchaseTopicRepository, SaleAdRepository saleAdRepository, BadgeRepository badgeRepository, NotificationRepository notificationRepository, PurchaseAdService purchaseAdService) {
        this.purchaseAdRepository = purchaseAdRepository;
        this.userService = userService;
        this.purchaseTopicRepository = purchaseTopicRepository;
        this.saleAdRepository = saleAdRepository;
        this.badgeRepository = badgeRepository;
        this.notificationRepository = notificationRepository;
        this.purchaseAdService = purchaseAdService;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findPurchaseAdById() {
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        Optional<PurchaseAd> result = purchaseAdService.findPurchaseAdById(1L);
        assertEquals(1, result.get().getPurchase_id());
    }

    @Test
    void findPurchaseAdByIdDto() {
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        PurchaseAdDto result = purchaseAdService.findPurchaseAdByIdDto(1L);
        assertEquals(1L, result.getUserId());
    }

    @Test
    void findAllPurchaseAds(){
        List<PurchaseAd> purchaseAds = new ArrayList<>();
        purchaseAds.add(purchaseAd());
        when(purchaseAdService.findAll()).thenReturn(purchaseAds);
        assertEquals(1, purchaseAds.size());
    }


    @Test
    void savePurchaseAdOptionalUserTrue(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.savePurchaseAd(purchaseAdDto());
        assertNotNull(result);
    }

    @Test
    void savePurchaseAdOptionalUserFalse(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.empty());
        PurchaseAdDto dto = purchaseAdService.savePurchaseAd(purchaseAdDto());
        assertNull(dto);
    }

    @Test
    void savePurchaseAdOptionalTopicTrue(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(purchaseTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        assertNotNull(purchaseAdService.savePurchaseAd(purchaseAdDto()));
    }

    @Test
    void savePurchaseAdOptionalTopicFalse(){
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(purchaseTopicRepository.findByName(any())).thenReturn(Optional.empty());
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        assertNotNull(purchaseAdService.savePurchaseAd(purchaseAdDto()));
    }

    @Test
    void savePurchaseAdNull(){
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.savePurchaseAd(purchaseAdDto());
        assertNull(result);
    }

    @Test
    void savePurchaseAdNotNull(){
        when(purchaseTopicRepository.findById(anyLong())).thenReturn(Optional.of(topic()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.savePurchaseAd(purchaseAdDto());
        assertNotNull(result);
    }

    @Test
    void updatePurchaseAdPresent(){
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.updatePurchaseAd(1L, purchaseAdDto());
        assertNotNull(result);
    }

    @Test
    void updatePurchaseAd(){
        when(purchaseAdService.findPurchaseAdById(anyLong())).thenReturn(Optional.empty());
        PurchaseAdDto result = purchaseAdService.updatePurchaseAd(purchaseAd().getPurchase_id(), purchaseAdDto());
        assertNull(result);
    }

    @Test
    void deletePurchaseAdTrue(){
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        assertTrue(purchaseAdService.deletePurchaseAd(1L));
    }

    @Test
    void deletePurchaseAdFalse(){
        boolean result = purchaseAdService.deletePurchaseAd(null);
        assertFalse(result);
    }

    @Test
    void deletePurchaseAdTopic(){
        Purchase_Topic topic = topic();
        purchaseAdService.deletePurchaseTopic(topic.getId());
        verify(purchaseTopicRepository).deleteById(1L);
    }

    // seeNotification

    @Test
    void seeNotificationPresent(){
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification()));
        when(notificationRepository.save(any())).thenReturn(notification());
        NotificationDto dto = purchaseAdService.seeNotification(1L);
        assertNotNull(dto);

    }
    @Test
    void seeNotification(){
        when(notificationRepository.findById(anyLong())).thenReturn(Optional.of(notification()));
        when(notificationRepository.save(any())).thenReturn(notification());
        NotificationDto notificationDto = purchaseAdService.seeNotification(1L);
        assertNotNull(notificationDto);
    }

    // markCompleteAd

    @Test
    void markCompleteAd(){
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(user()));
        when(saleAdRepository.findById(anyLong())).thenReturn(Optional.of(saleAd()));
        when(saleAdRepository.save(any())).thenReturn(saleAd());
        when(notificationRepository.save(any())).thenReturn(notification());
        when((purchaseAdRepository.save(any()))).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.markCompleteAd(1L, 1L, 1L);
        assertNotNull(result);
    }

    // receiveBadge

    @ParameterizedTest
    @ValueSource(ints = {25, 75, 150, 250})
    void receiveBadge(int verifiedAds){
        Badge badge = new Badge();
        badge.setId(1L);
        badge.setUsers(new HashSet<>());
        User user = user();
        user.setVerified_ads(verifiedAds);
        when(badgeRepository.save(badge)).thenReturn(badge);
        purchaseAdService.receiveBadge(Optional.of(user));
        verify(badgeRepository).save(any());
    }

    // Sort

    @Test
    void sortSaleAdsByOption(){
        when(purchaseAdRepository.findById(anyLong())).thenReturn(Optional.of(purchaseAd()));
        when(purchaseAdRepository.save(any())).thenReturn(purchaseAd());
        PurchaseAdDto result = purchaseAdService.sortSaleAdsByOption(1L, "asc", "popularity");
        assertNotNull(result);
    }

    // Filter

    @Test
    void filterSaleAdsByDate(){
        List<SaleAd> saleAdListList = new ArrayList<>();
        saleAdListList.add(saleAd());
        when(saleAdRepository.findAll()).thenReturn(saleAdListList);
        List<SaleAdDto> saleAds = purchaseAdService.filterSaleAdsByDate("week");
        assertEquals(0, saleAds.size());
    }



}
