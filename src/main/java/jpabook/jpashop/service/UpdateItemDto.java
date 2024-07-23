package jpabook.jpashop.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateItemDto {
    private String name;
    private int price;
    private int stockQuantity;

    public static UpdateItemDto createUpdateItemDto(String name,  int price, int stockQuantity) {
        UpdateItemDto itemDto = new UpdateItemDto();
        itemDto.setName(name);
        itemDto.setPrice(price);
        itemDto.setStockQuantity(stockQuantity);
        return itemDto;
    }
}