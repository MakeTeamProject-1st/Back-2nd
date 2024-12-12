package com.example.coupang.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {

    private int itemId;
    private int cartItemQuantity;


    public CartDTO(int itemId, int cartItemQuantity) {
        this.itemId = itemId;
        this.cartItemQuantity = cartItemQuantity;
    }
}
