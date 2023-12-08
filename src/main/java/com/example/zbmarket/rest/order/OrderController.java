package com.example.zbmarket.rest.order;

import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.rest.order.model.ResponseOrderCreateDto;
import com.example.zbmarket.service.order.OrderService;
import com.example.zbmarket.service.order.model.OrderCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseOrderCreateDto createOrder(
            @RequestBody RequestOrderCreateDto requestOrderCreateDto,
            Authentication authentication
    ) {
        OrderCreated orderCreated = orderService.createOrder(requestOrderCreateDto, authentication.getName());
        return ResponseOrderCreateDto.from(orderCreated);
    }
}
