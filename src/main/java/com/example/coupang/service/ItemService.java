package com.example.coupang.service;

import com.example.coupang.dto.ItemDTO;
import com.example.coupang.entity.ItemEntity;
import com.example.coupang.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 전체 물품 조회 (페이징 처리 포함)
    public Page<ItemDTO> getAllItems(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemEntity> items = itemRepository.findAll(pageable);
        return items.map(this::convertToItemDTO); // 변환 후 Page로 반환
    }


    // 물품 상세 조회
    public ItemDTO getItemDetails(String itemName) {
        ItemEntity itemEntity = itemRepository.findByItemName(itemName);
        if (itemEntity == null || itemEntity.getItemStock() == 0) {
            return null; // 물품이 없거나 재고가 없으면 null 반환
        }
        return convertToItemDTO(itemEntity);
    }

    // ItemEntity -> ItemDTO 변환
    private ItemDTO convertToItemDTO(ItemEntity itemEntity) {
        return new ItemDTO(
                itemEntity.getItemId(),
                itemEntity.getItemName(),
                itemEntity.getItemOption(),
                itemEntity.getItemPrice(),
                itemEntity.getItemStock(),
                itemEntity.getItemImage(),
                itemEntity.getItemExplain()
        );
    }

}