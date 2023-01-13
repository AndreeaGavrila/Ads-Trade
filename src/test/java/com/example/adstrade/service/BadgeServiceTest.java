package com.example.adstrade.service;

import com.example.adstrade.enums.BadgeType;
import com.example.adstrade.model.Badge;
import com.example.adstrade.model.dto.BadgeDto;
import com.example.adstrade.repository.BadgeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.adstrade.model.BadgeMock.badge;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class BadgeServiceTest {

    @Mock
    private final BadgeRepository badgeRepository;

    @InjectMocks
    private BadgeService badgeService;

    public BadgeServiceTest(BadgeRepository badgeRepository, BadgeService badgeService) {
        this.badgeRepository = badgeRepository;
        this.badgeService = badgeService;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void findBadgeById() {
        when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge()));
        Optional<Badge> result = badgeService.findBadgeById(1L);
        assertEquals(1, result.get().getId());
    }

    @Test
    void findBadgeByIdDto() {
        when(badgeRepository.findById(anyLong())).thenReturn(Optional.of(badge()));
        BadgeDto result = badgeService.findBadgeByIdDto(1L);
        assertEquals(1, result.getClass());
    }

    @Test
    void findAllBadges() {
        List<Badge> badges = new ArrayList<>();
        badges.add(badge());
        when(badgeService.findAllBadges()).thenReturn(new ArrayList<>());
        assertEquals(1, badges.size());
    }

    @Test
    void findAllBadgesByName() {
        when(badgeRepository.findAllByName(BadgeType.valueOf(""))).thenReturn(new ArrayList<>());
        List<Badge> badges = badgeRepository.findAllByName(BadgeType.valueOf(""));
        assertEquals(1, badgeRepository.findAllByName(BadgeType.valueOf("")).size());
    }

    @Test
    void findAll() {
        when(badgeRepository.findAll()).thenReturn(new ArrayList<>());
        assertEquals(0, badgeService.findAll().size());
    }

}
