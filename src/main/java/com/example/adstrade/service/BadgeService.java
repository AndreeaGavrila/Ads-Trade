package com.example.adstrade.service;

import com.example.adstrade.enums.BadgeType;
import com.example.adstrade.model.Badge;
import com.example.adstrade.model.dto.BadgeDto;
import com.example.adstrade.repository.BadgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;

    @Autowired
    public BadgeService(BadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Transactional
    public Optional<Badge> findBadgeById(Long id) {
        return badgeRepository.findById(id);
    }

    @Transactional
    public List<BadgeDto> findAllBadges() {
        List<Badge> badges = badgeRepository.findAll();
        return badges.stream().map(BadgeDto::convertEntityToDto).collect(toList());
    }

    @Transactional
    public List<BadgeDto> findAllBadgesByName(BadgeType name) {
        List<Badge> badges = badgeRepository.findAllByName(name);
        return badges.stream().map(BadgeDto::convertEntityToDto).collect(toList());
    }

    @Transactional
    public BadgeDto findBadgeByIdDto(Long id) {
        Optional<Badge> optionalBadge = findBadgeById(id);
        return optionalBadge.map(BadgeDto::convertEntityToDto).orElse(null);
    }

    @Transactional
    public List<Badge> findAll(){
        return badgeRepository.findAll();
    }
}
