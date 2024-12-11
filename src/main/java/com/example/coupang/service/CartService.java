package com.example.coupang.service;

import com.example.coupang.dto.CartDTO;
import com.example.coupang.entity.CartItemEntity;
import com.example.coupang.entity.ItemEntity;
import com.example.coupang.repository.CartRepository;
import com.example.coupang.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;

    // 장바구니에 물품 담기
    public boolean addToCart(CartDTO cartDTO) {
        // 물품을 조회
        Optional<ItemEntity> itemEntityOpt = itemRepository.findById(cartDTO.getItemId());
        if (itemEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("아이템을 찾을 수 없습니다. 아이템 ID: " + cartDTO.getItemId());
        }

        ItemEntity itemEntity = itemEntityOpt.get();
        // 재고가 충분한지 확인
        if (itemEntity.getItemStock() >= cartDTO.getCartItemQuantity()) {
            // 장바구니에 물품 추가
            CartItemEntity cartItemEntity = new CartItemEntity(itemEntity, cartDTO.getCartItemQuantity());
            cartRepository.save(cartItemEntity);  // 저장
            return true;
        } else {
            throw new IllegalArgumentException("재고가 부족합니다. 아이템 ID: " + cartDTO.getItemId());
        }
    }

    // 장바구니 물품 내역 수정
    public boolean modifyCartItem(CartDTO cartDTO) {
        // 장바구니에서 해당 itemId를 가진 항목 조회
        Optional<CartItemEntity> cartItemEntityOpt = cartRepository.findById(cartDTO.getItemId());
        if (cartItemEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다. 아이템 ID: " + cartDTO.getItemId());
        }

        CartItemEntity cartItemEntity = cartItemEntityOpt.get();
        // 해당 물품의 ItemEntity 조회
        Optional<ItemEntity> itemEntityOpt = itemRepository.findById(cartDTO.getItemId());
        if (itemEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("아이템을 찾을 수 없습니다. 아이템 ID: " + cartDTO.getItemId());
        }

        ItemEntity itemEntity = itemEntityOpt.get();
        // 재고가 충분한지 확인
        if (itemEntity.getItemStock() >= cartDTO.getCartItemQuantity()) {
            // 수량 수정
            cartItemEntity.setCartItemQuantity(cartDTO.getCartItemQuantity());
            cartRepository.save(cartItemEntity);  // 수정된 장바구니 항목 저장
            return true;
        } else {
            throw new IllegalArgumentException("재고가 부족합니다. 아이템 ID: " + cartDTO.getItemId());
        }
    }

    // 장바구니 물품 리스트 조회
    public List<CartDTO> getCartItems() {
        // 모든 장바구니 항목 조회
        List<CartItemEntity> cartItemEntities = cartRepository.findAll();
        // DTO로 반환
        return cartItemEntities.stream()
                .map(cartItemEntity -> new CartDTO(cartItemEntity.getCartItem().getItemId(), cartItemEntity.getCartItemQuantity()))
                .collect(Collectors.toList());
    }

    // 장바구니 주문
    public boolean placeOrder() {
        // 장바구니에 있는 모든 항목을 조회
        List<CartItemEntity> cartItemEntities = cartRepository.findAll();
        // 각 항목의 재고가 충분한지 확인
        for (CartItemEntity cartItemEntity : cartItemEntities) {
            ItemEntity itemEntity = cartItemEntity.getCartItem();
            if (itemEntity.getItemStock() < cartItemEntity.getCartItemQuantity()) {
                throw new IllegalArgumentException("재고가 부족합니다. 아이템 ID: " + itemEntity.getItemId());
            }
        }

        // 주문 처리 (결제 및 재고 차감)
        for (CartItemEntity cartItemEntity : cartItemEntities) {
            ItemEntity itemEntity = cartItemEntity.getCartItem();
            // 재고 차감
            itemEntity.setItemStock(itemEntity.getItemStock() - cartItemEntity.getCartItemQuantity());
            itemRepository.save(itemEntity);  // 재고 업데이트
        }
        // 장바구니 비우기
        cartRepository.deleteAll();
        return true; // 주문 성공
    }

    // 장바구니에서 항목 삭제
    public boolean removeCartItem(int itemId) {
        // 해당 아이템을 장바구니에서 삭제
        Optional<CartItemEntity> cartItemEntityOpt = cartRepository.findById(itemId);
        if (cartItemEntityOpt.isPresent()) {
            cartRepository.delete(cartItemEntityOpt.get());
            return true;
        } else {
            throw new IllegalArgumentException("장바구니 항목을 찾을 수 없습니다. 아이템 ID: " + itemId);
        }
    }

}
