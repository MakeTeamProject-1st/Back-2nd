package com.example.coupang.repository;

import com.example.coupang.entity.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<CartItemEntity, Integer> {

    List<CartItemEntity> findByCartItem_ItemId(int itemId); // 장바구니 항목을 ItemId로 찾기
}
