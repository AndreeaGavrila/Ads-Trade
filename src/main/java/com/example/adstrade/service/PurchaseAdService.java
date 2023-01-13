package com.example.adstrade.service;

import com.example.adstrade.enums.*;
import com.example.adstrade.model.*;
import com.example.adstrade.model.dto.*;
import com.example.adstrade.repository.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

import static com.example.adstrade.model.dto.PurchaseAdDto.convertEntityToDto;
import static java.util.stream.Collectors.toList;

@Service
public class PurchaseAdService {

    private final PurchaseAdRepository purchaseAdRepository;
    private final UserService userService;
    private final PurchaseTopicRepository purchaseTopicRepository;
    private final SaleAdRepository saleAdRepository;
    private final BadgeRepository badgeRepository;
    private final NotificationRepository notificationRepository;

    @Autowired
    public PurchaseAdService(PurchaseAdRepository purchaseAdRepository,
                             UserService userService,
                             SaleAdRepository saleAdRepository,
                             PurchaseTopicRepository purchaseTopicRepository,
                             BadgeRepository badgeRepository,
                             NotificationRepository notificationRepository) {
        this.purchaseAdRepository = purchaseAdRepository;
        this.userService = userService;
        this.saleAdRepository = saleAdRepository;
        this.purchaseTopicRepository = purchaseTopicRepository;
        this.badgeRepository = badgeRepository;
        this.notificationRepository = notificationRepository;
    }

    @Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    public Optional<PurchaseAd> findPurchaseAdById (Long id) {
        return purchaseAdRepository.findById(id);
    }

    @Transactional
    public PurchaseAdDto findPurchaseAdByIdDto (Long id) {
        Optional<PurchaseAd> optionalPurchaseAd = findPurchaseAdById(id);
        return optionalPurchaseAd.map(PurchaseAdDto::convertEntityToDto).orElse(null);
    }

    @Transactional
    public List<PurchaseAd> findAll (){
        return purchaseAdRepository.findAll();
    }


    @Transactional
    public PurchaseAdDto savePurchaseAd (@NotNull PurchaseAdDto purchaseAdDto){
        PurchaseAd purchaseAd = new PurchaseAd();
        Optional<User> optionalUser = userService.findUserById(purchaseAdDto.getUserId());

        TopicValue t = TopicValue.valueOf(purchaseAdDto.getPurchase_topic().toUpperCase(Locale.ROOT));
        Optional<Purchase_Topic> optionalTopic = purchaseTopicRepository.findByName(t);

        if(optionalUser.isPresent()){
            //save purchase ad
            purchaseAd.setMessage(purchaseAdDto.getMessage());
            purchaseAd.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            purchaseAd.setUser(optionalUser.get());

            if(optionalTopic.isPresent()){
                purchaseAd.setPurchase_topic(optionalTopic.get());
            }
            else{
                Purchase_Topic topic = new Purchase_Topic();
                topic.setName(TopicValue.valueOf(purchaseAdDto.getPurchase_topic().toUpperCase()));
                topic = purchaseTopicRepository.save(topic);
                purchaseAd.setPurchase_topic(topic);
            }
            purchaseAd = purchaseAdRepository.save(purchaseAd);
            return convertEntityToDto(purchaseAd);
        }
        return null;
    }

    @Transactional
    public PurchaseAdDto updatePurchaseAd (Long purchaseAdId, PurchaseAdDto purchaseAdDto){
        Optional<PurchaseAd> optionalPurchaseAd = findPurchaseAdById(purchaseAdId);
        if(optionalPurchaseAd.isPresent()){
            PurchaseAd purchaseAd = optionalPurchaseAd.get();
            purchaseAd.setMessage(purchaseAdDto.getMessage());
            purchaseAd.setDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
            return convertEntityToDto(purchaseAdRepository.save(purchaseAd));
        }
        return null;
    }

    @Transactional
    public boolean deletePurchaseAd (Long purchaseAdId){
        if(purchaseAdId != null){
            Optional<PurchaseAd> optionalPurchaseAd = findPurchaseAdById(purchaseAdId);
            if(optionalPurchaseAd.isPresent()){
                purchaseAdRepository.delete(optionalPurchaseAd.get());
                return true;
            }
        }
        return false;
    }

    @Transactional
    public void deletePurchaseTopic(Long topicId){
        purchaseTopicRepository.deleteById(topicId);
    }

    @Transactional
    public NotificationDto seeNotification (Long notificationId){
        Optional<Notification> optionalNotification = notificationRepository.findById(notificationId);
        if(optionalNotification.isPresent()){
            Notification notification = optionalNotification.get();
            notification.setNotification_status(NotificationStatus.SEEN);
            notification = notificationRepository.save(notification);
            return new NotificationDto(notification.getNotification_type().toString(),
                    notification.getNotification_status(),
                    notification.getPurchaseAd().getPurchase_id() );
        }
        return null;
    }


    @Transactional
    public PurchaseAdDto markCompleteAd (Long purchaseAdId, Long userId, Long saleAdId){
        Optional<PurchaseAd> optionalPurchaseAd = findPurchaseAdById(purchaseAdId);
        Optional<User> optionalUser = userService.findUserById(userId);

        if(optionalPurchaseAd.isPresent() && optionalUser.isPresent()){
            PurchaseAd purchaseAd = optionalPurchaseAd.get();

            if(purchaseAd.getUser().getId().equals(optionalUser.get().getId())){
                Optional<SaleAd> optionalSaleAd = saleAdRepository.findById(saleAdId);

                if(optionalSaleAd.isPresent() && optionalSaleAd.get().getPurchaseAd().getPurchase_id().equals(purchaseAdId)){
                    SaleAd saleAd = optionalSaleAd.get();
                    purchaseAd.setCompleted(true);
                    saleAdRepository.save(saleAd);

                    if(saleAd.getLikes() >= 5){
                        Optional<User> ratedUser = userService.findUserById(saleAd.getUser().getId());
                        if(ratedUser.isPresent()){
                            receiveBadge(ratedUser);
                        }
                    }
                    if(saleAd.getLikes() >= 10){
                        if(!saleAd.getStatus().equals(SaleStatus.CLOSED)){
                            Notification acceptPurchaseAddReminder = new Notification();
                            acceptPurchaseAddReminder.setPurchaseAd(saleAd.getPurchaseAd());
                            acceptPurchaseAddReminder.setNotification_type(NotificationType.REMINDER_CLOSE_SALE_AD);
                            acceptPurchaseAddReminder.setNotification_status(NotificationStatus.UNSEEN);
                            notificationRepository.save(acceptPurchaseAddReminder);
                        }
                    }
                    return convertEntityToDto(purchaseAdRepository.save(purchaseAd));
                }
            }
        }
        return null;
    }


    @Transactional
    public void receiveBadge (Optional<User> ratedUser) {
        if(ratedUser.isPresent()){
            User ratedUser1 = ratedUser.get();
            ratedUser1.setVerified_ads(ratedUser1.getVerified_ads() + 1);

            if(ratedUser1.getVerified_ads() >= 5){
                Badge newBadge = new Badge();

                if(ratedUser1.getVerified_ads() <= 10){
                    newBadge.setName(BadgeType.BRONZE);
                    newBadge.setPoints(ratedUser1.getVerified_ads() * 2);
                }

                if(ratedUser1.getVerified_ads() > 10 && ratedUser1.getVerified_ads() <= 25){
                    newBadge.setName(BadgeType.SILVER);
                    newBadge.setPoints(ratedUser1.getVerified_ads() * 5);
                }

                if(ratedUser1.getVerified_ads() > 25 && ratedUser1.getVerified_ads() <= 50){
                    newBadge.setName(BadgeType.GOLD);
                    newBadge.setPoints(ratedUser1.getVerified_ads() * 10);
                }

                if(ratedUser1.getVerified_ads() > 50){
                    newBadge.setName(BadgeType.DIAMOND);
                    newBadge.setPoints(ratedUser1.getVerified_ads() * 20);
                }

                Set<Badge> userBadges = ratedUser1.getBadges();
                userBadges.add(newBadge);
                badgeRepository.save(newBadge);
                ratedUser1.setBadges(userBadges);

            }
            userService.insertUser(ratedUser1);
        }
    }

    @Transactional
    public PurchaseAdDto sortSaleAdsByOption (Long purchaseAdId, String option, String type){
        Optional<PurchaseAd> optionalPurchaseAd = findPurchaseAdById(purchaseAdId);

        if(optionalPurchaseAd.isPresent()){
            PurchaseAd purchaseAd = optionalPurchaseAd.get();
            List<SaleAd> sortedSaleAds = purchaseAd.getSaleAds();

            if(type.equals("asc")){
                if(option.equals("popularity")){
                    sortedSaleAds.sort(new SaleAd());
                    purchaseAd.setSaleAds(sortedSaleAds);
                }
                if(option.equals("date")) {

                    Collections.sort(sortedSaleAds);
                    purchaseAd.setSaleAds(sortedSaleAds);

                }
            }
            if(type.equals("desc")){
                if(option.equals("popularity")){
                    sortedSaleAds.sort(Collections.reverseOrder(new SaleAd()));
                    purchaseAd.setSaleAds(sortedSaleAds);
                }
                if(option.equals("date")) {
                    sortedSaleAds.sort(Collections.reverseOrder());
                    purchaseAd.setSaleAds(sortedSaleAds);

                }
            }
            return PurchaseAdDto.convertEntityToDto(purchaseAdRepository.save(purchaseAd));
        }
        return null;
    }

    @Transactional
    public List<SaleAdDto> filterSaleAdsByDate( @NotNull String criteria){
        List<SaleAd> saleAds = saleAdRepository.findAll();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        if(criteria.equalsIgnoreCase("week")){
            saleAds = saleAds.stream().filter(answer -> {
                LocalDateTime localDateTime = LocalDateTime.parse(answer.getDate(), dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();
                WeekFields weekFields = WeekFields.ISO;

                int weekNumberForAnswerDate = localDateTime.get(weekFields.weekOfWeekBasedYear());
                int weekNumberForNow = now.get(weekFields.weekOfWeekBasedYear());

                return localDateTime.getYear() == now.getYear() &&
                        localDateTime.getMonthValue() == now.getMonthValue() &&
                        weekNumberForAnswerDate == weekNumberForNow &&
                        localDateTime.getDayOfWeek().getValue() <= now.getDayOfWeek().getValue();
            }).collect(toList());

        } else if(criteria.equalsIgnoreCase("month")) {
            saleAds = saleAds.stream().filter(answer -> {
                LocalDateTime localDateTime = LocalDateTime.parse(answer.getDate(), dateTimeFormatter);
                LocalDateTime now = LocalDateTime.now();

                return  localDateTime.getYear() == now.getYear() &&
                        localDateTime.getMonthValue() ==  now.getMonthValue() &&
                        localDateTime.getDayOfMonth() <= now.getDayOfMonth();
            }).collect(toList());

        }
        else if (criteria.equalsIgnoreCase("year")) {
            saleAds = saleAds.stream().filter(answer ->
                    LocalDateTime.parse(answer.getDate(), dateTimeFormatter).getYear() ==
                            LocalDate.now().getYear()
            ).collect(toList());

        } else
            return new ArrayList<>();

        return saleAds.stream().map(SaleAdDto::convertEntityToDto).collect(toList());
    }

}
