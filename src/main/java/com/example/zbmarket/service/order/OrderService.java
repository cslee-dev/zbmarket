package com.example.zbmarket.service.order;

import com.example.zbmarket.exception.ErrorCodeEnum;
import com.example.zbmarket.exception.GlobalException;
import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.OrderRepository;
import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.repository.entity.MemberOrderEntity;
import com.example.zbmarket.repository.entity.OrderProductEntity;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.rest.order.model.RequestOrderCreateDto;
import com.example.zbmarket.rest.order.model.RequestOrderProductCreateDto;
import com.example.zbmarket.service.order.model.OrderCanceled;
import com.example.zbmarket.service.order.model.OrderCreated;
import com.example.zbmarket.type.order.OrderStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    @Transactional
    public OrderCreated createOrder(RequestOrderCreateDto requestOrderCreateDto, String email) throws GlobalException {
        MemberEntity memberEntity = findMemberByEmail(email);
        OrderAccumulator accumulator = new OrderAccumulator();
        List<OrderProductEntity> orderProducts = createOrderProducts(requestOrderCreateDto, accumulator);
        MemberOrderEntity memberOrder = createNewOrder(memberEntity, orderProducts, accumulator);
        orderRepository.save(memberOrder);
        return OrderCreated.from(memberOrder);
    }

    public OrderCanceled cancelOrder(Long id, String email) throws GlobalException {
        MemberEntity memberEntity = findMemberByEmail(email);
        MemberOrderEntity find = orderRepository.findById(id)
                .orElseThrow(NullPointerException::new);

        if (!Objects.equals(find.getMember().getEmail(), memberEntity.getEmail())) {
            throw new GlobalException(ErrorCodeEnum.ORDER_OWNER_ERROR);
        }

        MemberOrderEntity changed = find.withStatus(OrderStatusEnum.CANCELED);
        orderRepository.save(changed);
        return OrderCanceled.from(changed);
    }

    private MemberOrderEntity createNewOrder(MemberEntity memberEntity, List<OrderProductEntity> orderProducts, OrderAccumulator accumulator) {
        return MemberOrderEntity.createNewOrder(memberEntity, orderProducts, accumulator);
    }

    private MemberEntity findMemberByEmail(String email) throws GlobalException {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum.NOT_FOUND_MEMBER_ERROR));
    }

    private List<OrderProductEntity> createOrderProducts(RequestOrderCreateDto requestOrderCreateDto, OrderAccumulator accumulator) {
        return requestOrderCreateDto.getOrderProducts()
                .stream()
                .map(request -> processOrderRequest(request, accumulator))
                .collect(Collectors.toList());
    }

    private OrderProductEntity processOrderRequest(RequestOrderProductCreateDto requestOrderProductCreateDto, OrderAccumulator accumulator) throws GlobalException {
        ProductEntity productEntity = findProductById(requestOrderProductCreateDto.getId());
        accumulator.accumulate(productEntity, requestOrderProductCreateDto.getQuantity());
        return OrderProductEntity.createNewOrderProduct(productEntity, requestOrderProductCreateDto.getQuantity(), productEntity.getPrice() * requestOrderProductCreateDto.getQuantity());
    }

    private ProductEntity findProductById(Long id) throws GlobalException {
        return productRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCodeEnum.NOT_FOUND_PRODUCT_ERROR));
    }
}
