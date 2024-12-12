package com.example.coupang.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "carts")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @ManyToOne
    @JoinColumn( nullable = false)
    private ItemEntity cartItem;

    @Column(nullable = false)
    private int cartItemQuantity; // 수량

    // 생성자
    public CartItemEntity(ItemEntity cartItem, int cartItemQuantity) {
        this.cartItem = cartItem;
        this.cartItemQuantity = cartItemQuantity;
    }

    public CartItemEntity() {}  // jpa 사용,,
}
