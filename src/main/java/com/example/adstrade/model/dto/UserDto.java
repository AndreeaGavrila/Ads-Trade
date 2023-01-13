package com.example.adstrade.model.dto;

import com.example.adstrade.enums.Role;
import com.example.adstrade.model.User;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Data
public class UserDto {
    private String email;
    private String password;

    private String firstName;
    private String lastName;

    private int verified_ads;
    private String description;

    @Enumerated(EnumType.STRING)
    private String role;

    private List<PurchaseAdDto> purchaseAds;
    private List<SaleAdDto> saleAds;
    private List <BadgeDto> usersBadges;

    public static @NotNull UserDto convertEntityToDto(@NotNull User user){
        UserDto dto = new UserDto();
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setDescription(user.getDescription());
        dto.setVerified_ads(user.getVerified_ads());
        dto.setRole(Role.ROLE_USER.toString());

        if (user.getPurchaseAds() != null) {
            dto.setPurchaseAds(user.getPurchaseAds().stream().map(PurchaseAdDto::convertEntityToDto).collect(toList()));
        }
        if (user.getSaleAds() != null) {
            dto.setSaleAds(user.getSaleAds().stream().map(SaleAdDto::convertEntityToDto).collect(toList()));
        }
        if (user.getBadges() != null) {
            dto.setUsersBadges(user.getBadges()
                    .stream().map(BadgeDto::convertEntityToDto).collect(toList()));
        }
        else {
            dto.setUsersBadges(new ArrayList<>());
        }
        return dto;
    }

}
