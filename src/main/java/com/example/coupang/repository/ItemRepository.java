package com.example.coupang.repository;

import com.example.coupang.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Integer> {

    ItemEntity findByItemName(String itemName);

    // Paging처리
    Page<ItemEntity> findAll(Pageable pageable); // 페이징 지원
}