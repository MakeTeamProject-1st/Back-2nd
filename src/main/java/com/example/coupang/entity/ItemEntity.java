package com.example.coupang.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "items")
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    @Column(nullable = false, length = 100)
    private String itemName;

    @Column(nullable = false)
    private String itemExplain;

    @Column(nullable = false)
    private int itemPrice;

    @Column(nullable = false)
    private int itemStock;

    private String itemOption;

    private String itemImage;
}