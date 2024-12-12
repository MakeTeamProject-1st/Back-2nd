
package com.example.coupang.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDTO {

    private int itemId; // 물품 ID
    private String itemName; // 물품 이름
    private int itemPrice; // 물품 가격
    private int itemStock; // 재고 수량
    private String itemOption; // 물품 옵션 (예: 색상, 사이즈)
    private String itemImage; // 물품 이미지 URL
    private String itemExplain; // 물품 설명 (예: 상세 설명)

    // 기본 생성자
    public ItemDTO() {
    }

    // 파라미터가 있는 생성자
    public ItemDTO(int itemId, String itemName, String itemOption, int itemPrice, int itemStock, String itemImage, String itemExplain) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.itemOption = itemOption; // 물품 옵션 (색상, 사이즈 등)
        this.itemImage = itemImage;
        this.itemExplain = itemExplain; // 물품 설명 (상세 정보)
    }
}