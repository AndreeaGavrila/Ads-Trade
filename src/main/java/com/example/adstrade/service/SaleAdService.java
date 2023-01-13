package com.example.adstrade.service;

import com.example.adstrade.enums.*;
import com.example.adstrade.model.*;
import com.example.adstrade.model.dto.*;
import com.example.adstrade.model.requests.VoteAdRequest;
import com.example.adstrade.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.adstrade.model.dto.SaleAdDto.convertEntityToDto;
import static java.util.stream.Collectors.toList;


@Service
public class SaleAdService {

    private final SaleAdRepository saleAdRepository;
    private final UserService userService;
    private final PurchaseAdService purchaseAdService;

    private final PurchaseAdRepository purchaseAdRepository;
    private final SaleTopicRepository saleTopicRepository;
    private final BadgeRepository badgeRepository;
    private final NotificationRepository notificationRepository;


    public SaleAdService(SaleAdRepository saleAdRepository,
                         UserService userService,
                         PurchaseAdService purchaseAdService,
                         PurchaseAdRepository purchaseAdRepository,
                         SaleTopicRepository saleTopicRepository,
                         BadgeRepository badgeRepository,
                         NotificationRepository notificationRepository) {
        this.saleAdRepository = saleAdRepository;
        this.userService = userService;
        this.purchaseAdService = purchaseAdService;
        this.purchaseAdRepository = purchaseAdRepository;
        this.saleTopicRepository = saleTopicRepository;
        this.badgeRepository = badgeRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Optional<SaleAd> findSaleAdById(Long id){
        return saleAdRepository.findById(id);
    }

    public List<SaleAd> findAll (){
        return saleAdRepository.findAll();
    }

    @Transactional
    public SaleAdDto findSaleAdByIdDto (Long id) {
        Optional<SaleAd> optionalSaleAd = findSaleAdById(id);
        return optionalSaleAd.map(SaleAdDto::convertEntityToDto).orElse(null);
    }

    @Transactional
    public SaleAdDto saveSaleAd(@NotNull SaleAdDto saleAdDto){
        SaleAd saleAd = new SaleAd();
        Optional<User> optionalUser = userService.findUserById(saleAdDto.getUserId());
        Optional<PurchaseAd> optionalPurchaseAd = purchaseAdService.findPurchaseAdById(saleAdDto.getPurchaseAdId());

        TopicValue t = TopicValue.valueOf(saleAdDto.getSale_topic().toUpperCase(Locale.ROOT));
        Optional<Sale_Topic> optionalTopic = saleTopicRepository.findByName(t);

        if(optionalUser.isPresent() && optionalPurchaseAd.isPresent()){
            //saving sale ad
            saleAd.setMessage(saleAdDto.getMessage());
            saleAd.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            saleAd.setStatus(SaleStatus.OPEN);

            saleAd.setUser(optionalUser.get());
            saleAd.setPurchaseAd(optionalPurchaseAd.get());

            if(optionalTopic.isPresent()){
                saleAd.setSale_topic(optionalTopic.get());
            }
            else{
                Sale_Topic topic = new Sale_Topic();
                topic.setName(TopicValue.valueOf(saleAdDto.getSale_topic().toUpperCase()));
                topic = saleTopicRepository.save(topic);
                saleAd.setSale_topic(topic);
            }

            saleAd = saleAdRepository.save(saleAd);

            //send notification
            Notification notification = new Notification();
            notification.setPurchaseAd(optionalPurchaseAd.get());
            notification.setSaleAd(saleAd);
            notification.setNotification_type(NotificationType.RECEIVED_SALE_AD);
            notification.setNotification_status(NotificationStatus.UNSEEN);
            notificationRepository.save(notification);

            return SaleAdDto.convertEntityToDto(saleAd);
        }
        return null;
    }

    @Transactional
    public SaleAdDto updateSaleAd(Long saleAdId, SaleAdDto saleAdDto){
        Optional<SaleAd> optionalSaleAd = findSaleAdById(saleAdId);
        if(optionalSaleAd.isPresent()){
            SaleAd saleAd = optionalSaleAd.get();
            saleAd.setMessage(saleAdDto.getMessage());
            saleAd.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            return convertEntityToDto(saleAdRepository.save(saleAd));
        }
        return null;
    }

    @Transactional
    public boolean deleteSaleAd(Long saleAdId){
        if(saleAdId != null){
            Optional<SaleAd> optionalSaleAd = findSaleAdById(saleAdId);
            if(optionalSaleAd.isPresent()){
                saleAdRepository.delete(optionalSaleAd.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deleteSaleTopic(Long topicId){
        saleTopicRepository.deleteById(topicId);
    }

    @Transactional
    public List<SaleAdDto> getAllSalesForPurchase(Long saleAdId) {
        Optional<PurchaseAd> optionalPurchaseAd = purchaseAdService.findPurchaseAdById(saleAdId);
        return optionalPurchaseAd.map(purchaseAd -> purchaseAd.getSaleAds().stream()
                .map(SaleAdDto::convertEntityToDto).collect(toList())).orElseGet(ArrayList::new);
    }


    @Transactional
    public SaleAdDto changeSaleAdStatus(Long saleAdId){
        if(saleAdId != null){
            Optional<SaleAd> optionalSaleAd = findSaleAdById(saleAdId);

            if(optionalSaleAd.isPresent()){
                SaleAd saleAd = optionalSaleAd.get();
                if(saleAd.getStatus().equals(SaleStatus.OPEN) && saleAd.getLikes() >= 10)
                {
                    saleAd.setStatus(SaleStatus.CLOSED);
                }
                else if(saleAd.getStatus().equals(SaleStatus.CLOSED))
                {
                    saleAd.setStatus(SaleStatus.OPEN);
                }
                saleAd = saleAdRepository.save(saleAd);
                return convertEntityToDto(saleAd);
            }
        }
        return null;
    }

    @Transactional
    public SaleAdDto closeSaleAd (Long saleAdId, Long userId){
        Optional<SaleAd> optionalSaleAd = findSaleAdById(saleAdId);
        Optional<User> optionalUser = userService.findUserById(userId);

        if(optionalSaleAd.isPresent() && optionalUser.isPresent()) {
            SaleAd saleAd = optionalSaleAd.get();

            if (saleAd.getUser().getId().equals(optionalUser.get().getId())) {

                    if(saleAd.getPurchaseAd().isCompleted() && saleAd.getLikes() >= 10){
                        saleAd.setStatus(SaleStatus.CLOSED);
                        return convertEntityToDto(saleAdRepository.save(saleAd));
                    }

            }
        }
        return null;
    }


    @Transactional
    public SaleAdDto voteSaleAd(@NotNull VoteAdRequest request){
        Optional<SaleAd> optionalSaleAd = findSaleAdById(request.getAdId());
        Optional<User> optionalUser = userService.findUserById(request.getUserId());
        Optional<PurchaseAd> optionalPurchaseAd = purchaseAdService.findPurchaseAdById(request.getAdId());

        if(optionalSaleAd.isPresent() && optionalUser.isPresent() &&
                (request.getOption() == 0 || request.getOption() == 1)){

            SaleAd saleAd = optionalSaleAd.get();
            User user = optionalUser.get();
            PurchaseAd purchaseAd = optionalPurchaseAd.get();

            Optional<SaleAd> votedSaleAd = user.getVotedSaleAds().stream().filter(a ->
                    a.getSale_id().equals(request.getAdId())).findFirst();

            //check if user already voted the ad
            if(votedSaleAd.isEmpty()){
                if(request.getOption() == 0){
                    saleAd.setDislikes(saleAd.getDislikes() + 1);
                }
                else {
                    saleAd.setLikes(saleAd.getLikes() + 1);
                    Optional<User> ratedUser = userService.findUserById(saleAd.getUser().getId());

                    if(saleAd.getLikes() >= 9 && ratedUser.isPresent()){
                        purchaseAdService.receiveBadge(ratedUser);
                    }
                }

                //send reminder to accept the sale ad if number of likes is greater than 10 && purchase Ad is completed
                if(saleAd.getLikes() >= 10){
                    if(!purchaseAd.isCompleted()){

                        Notification acceptSaleAdReminder = new Notification();
                        //acceptSaleAdReminder.setSaleAd(saleAd.getSaleAd());
                        acceptSaleAdReminder.setSaleAd(optionalSaleAd.get());
                        acceptSaleAdReminder.setPurchaseAd(optionalPurchaseAd.get());
                        acceptSaleAdReminder.setNotification_type(NotificationType.REMINDER_ACCEPT_SALE_AD);
                        acceptSaleAdReminder.setNotification_status(NotificationStatus.UNSEEN);
                        notificationRepository.save(acceptSaleAdReminder);
                    }
                    else{
                        if(!saleAd.getStatus().equals(SaleStatus.CLOSED)){
                            Notification acceptSaleAdReminder = new Notification();
                            //acceptSaleAdReminder.setSaleAd(saleAd.getSaleAd());
                            acceptSaleAdReminder.setSaleAd(optionalSaleAd.get());
                            acceptSaleAdReminder.setPurchaseAd(optionalPurchaseAd.get());
                            acceptSaleAdReminder.setNotification_type(NotificationType.REMINDER_CLOSE_SALE_AD);
                            acceptSaleAdReminder.setNotification_status(NotificationStatus.UNSEEN);
                            notificationRepository.save(acceptSaleAdReminder);
                        }
                    }
                }

                user.addVotedSaleAd(saleAd);
                userService.insertUser(user);
                saleAd = saleAdRepository.save(saleAd);
                return convertEntityToDto(saleAd);
            }
        }
        return null;
    }

}

