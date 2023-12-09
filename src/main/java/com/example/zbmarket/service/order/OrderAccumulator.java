package com.example.zbmarket.service.order;

import com.example.zbmarket.repository.entity.ProductEntity;
import lombok.Getter;

@Getter
public class OrderAccumulator {
    long quantity;
    long price;

    void accumulate(ProductEntity productEntity, long quantity) {
        this.quantity += quantity;
        this.price += productEntity.getPrice() * quantity;
    }

}
