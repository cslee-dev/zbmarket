package com.example.zbmarket.service.order;

import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.OrderRepository;
import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.repository.entity.MemberOrderEntity;
import com.example.zbmarket.repository.entity.OrderProductEntity;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.service.order.model.OrderCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderCreated createOrder(RequestOrderCreateDto requestOrderCreateDto, String email) {
        // 주문서 생성자 가져온다.
        MemberEntity memberEntity = memberRepository.findByEmail(email)
                .orElseThrow(NullPointerException::new);

        // 주문 상품 정보를 누적하기 위함
        OrderAccumulator accumulator = new OrderAccumulator();
        // 유저가 주문한 상품정보를 저장한다.
        List<OrderProductEntity> orderProducts = requestOrderCreateDto.getOrderProducts()
                .stream()
                .map(request -> {
                    // 상품을 조회한다
                    ProductEntity productEntity = productRepository.findById(request.getId())
                            .orElseThrow(NullPointerException::new);
                    // Todo feat 재고 확인
                    // 주문서에 총 금액을 기재하기 위해 금액 누적
                    accumulator.accumulate(productEntity, request.getQuantity());
                    // 주문 상품 객체 생성
                    return OrderProductEntity.createNewOrderProduct(
                            productEntity, request.getQuantity()
                            , productEntity.getPrice() * request.getQuantity());
                }).collect(Collectors.toList());

        MemberOrderEntity memberOrder = MemberOrderEntity.createNewOrder(
                memberEntity,
                orderProducts,
                accumulator
        );

        MemberOrderEntity saved = orderRepository.save(memberOrder);
        return OrderCreated.from(saved);
    }
}
