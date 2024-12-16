package com.example.coupang.controller;

import com.example.coupang.dto.ItemDTO;
import com.example.coupang.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

@Tag(name = "Item API", description = "상품관련 API")
@RestController
@RequestMapping("/api/items")
public class ItemApiController {

    private final ItemService itemService;

    public ItemApiController(ItemService itemService) {
        this.itemService = itemService;
    }

    // 전체 물품 조회 (Pagination)
    @GetMapping("/all")
    public ResponseEntity<Page<ItemDTO>> getAllItems(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size ) {

        // Pagination을 적용하여 물품 리스트 조회
        Page<ItemDTO> items = itemService.getAllItems(page, size);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(items);  // 204 No Content
        } else {
            return ResponseEntity.ok(items);  // 200 OK
        }
    }

    // 물품 상세 조회하기
    @GetMapping
    public ResponseEntity<ItemDTO> getItemDetails(@RequestParam String itemName) {
        ItemDTO item = itemService.getItemDetails(itemName);

        if(item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 404 Not Found
        } else {
            return ResponseEntity.ok(item);  // 200 OK
        }
    }
}
