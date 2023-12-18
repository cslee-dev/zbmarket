package com.example.zbmarket.rest.order;

import com.example.zbmarket.exception.GlobalException;
import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.rest.order.model.ResponseOrderCancelDto;
import com.example.zbmarket.rest.order.model.ResponseOrderCreateDto;
import com.example.zbmarket.service.order.OrderService;
import com.example.zbmarket.service.order.model.OrderCanceled;
import com.example.zbmarket.service.order.model.OrderCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseOrderCreateDto createOrder(
            @RequestBody RequestOrderCreateDto requestOrderCreateDto,
            Authentication authentication
    ) throws GlobalException {
        OrderCreated orderCreated = orderService.createOrder(requestOrderCreateDto, authentication.getName());
        return ResponseOrderCreateDto.from(orderCreated);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseOrderCancelDto cancelOrder(
            @PathVariable Long orderId,
            Authentication authentication
    ) throws GlobalException {
        OrderCanceled orderCanceled = orderService
                .cancelOrder(orderId, authentication.getName());
        return ResponseOrderCancelDto.from(orderCanceled);
    }
}
