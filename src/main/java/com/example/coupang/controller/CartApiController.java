package com.example.coupang.controller;

import com.example.coupang.dto.CartDTO;
import com.example.coupang.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart API", description = "장바구니 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartApiController {

    private final CartService cartService;

    // 장바구니에 물품 담기
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestBody CartDTO cartDTO) {
        boolean isAdded = cartService.addToCart(cartDTO);
        if (isAdded) {
            return ResponseEntity.ok("장바구니에 물품을 추가했습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("장바구니 추가 실패 했습니다.");
    }

    // 장바구니 물품 내역 수정
    @PutMapping("/modify")
    public ResponseEntity<String> modifyCartItem(@RequestBody CartDTO cartDTO) {
        boolean isUpdated = cartService.modifyCartItem(cartDTO);
        if (isUpdated) {
            return ResponseEntity.ok("장바구니 물품이 성공적으로 수정되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("장바구니 수정에 실패했습니다.");
    }

    // 장바구니 물품 리스트 조회
    @GetMapping("/check")
    public ResponseEntity<List<CartDTO>> getCartItems() {
        List<CartDTO> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // 장바구니가 비었을 경우 204
        }
        return ResponseEntity.ok(cartItems); // 장바구니 물품 리스트 반환
    }

    // 장바구니 주문
    @PostMapping("/order")
    public ResponseEntity<String> placeOrder() {
        boolean isOrderPlaced = cartService.placeOrder();
        if (isOrderPlaced) {
            return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문에 실패했습니다.");
    }
}
