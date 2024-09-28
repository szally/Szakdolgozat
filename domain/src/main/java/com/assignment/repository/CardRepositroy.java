package com.assignment.repository;

import com.assignment.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepositroy extends JpaRepository<Card, Long> {
    List<Card> findAll();

    Card getCardById(long cardId);
}