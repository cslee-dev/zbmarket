package com.example.zbmarket.service.order;

import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.OrderRepository;
import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.repository.entity.MemberOrderEntity;
import com.example.zbmarket.repository.entity.OrderProductEntity;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.rest.order.model.RequestOrderProductCreateDto;
import com.example.zbmarket.service.order.model.OrderCreated;
import com.example.zbmarket.type.order.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderCreated createOrder(RequestOrderCreateDto requestOrderCreateDto, String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email).orElseThrow(
                NullPointerException::new
        );
        LocalDateTime now = LocalDateTime.now();
        long orderQuantity = 0L;
        long orderPrice = 0L;
        MemberOrderEntity order = MemberOrderEntity.builder()
                .status(OrderStatusEnum.ORDERED)
                .member(memberEntity)
                .orderProducts(new ArrayList<>())
                .createdAt(now)
                .updatedAt(now)
                .build();
        for (RequestOrderProductCreateDto dto : requestOrderCreateDto.getOrderProducts()) {
            ProductEntity productEntity = productRepository.findById(dto.getId()).orElseThrow(
                    NullPointerException::new
            );
            long orderProductPrice = productEntity.getPrice() * dto.getQuantity();
            orderQuantity += dto.getQuantity();
            orderPrice += orderProductPrice;
            order.getOrderProducts().add(
                    OrderProductEntity.builder()
                            .order(order)
                            .productName(productEntity.getName())
                            .quantity(dto.getQuantity())
                            .price(orderProductPrice)
                            .createdAt(now)
                            .updatedAt(now)
                            .build()
            );
        }
        order.setPrice(orderPrice);
        order.setQuantity(orderQuantity);
        orderRepository.save(order);
        return OrderCreated.from(order);
    }
}
