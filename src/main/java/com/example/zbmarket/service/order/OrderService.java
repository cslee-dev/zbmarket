package com.example.zbmarket.service.order;

import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.service.order.model.OrderCreated;

public interface OrderService {
    OrderCreated createOrder(
            RequestOrderCreateDto requestOrderCreateDto, String email);
}
