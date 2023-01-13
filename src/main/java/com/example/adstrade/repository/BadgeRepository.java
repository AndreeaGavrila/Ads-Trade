package com.example.adstrade.repository;

import com.example.adstrade.enums.BadgeType;
import com.example.adstrade.model.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {

    List<Badge> findAllByName(BadgeType name);
}
