package com.example.zbmarket.service.order;

import com.example.zbmarket.exception.GlobalException;
import com.example.zbmarket.repository.MemberRepository;
import com.example.zbmarket.repository.OrderRepository;
import com.example.zbmarket.repository.entity.MemberEntity;
import com.example.zbmarket.repository.entity.MemberOrderEntity;
import com.example.zbmarket.service.order.model.OrderCanceled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    private OrderService orderService;
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private OrderRepository orderRepository;

    private MemberEntity memberEntity;

    private MemberOrderEntity memberOrderEntity;

    @BeforeEach
    void setUp() {
        memberEntity = MemberEntity.builder().email("user@example.com").build();
        memberOrderEntity = MemberOrderEntity.builder()
                .id(1L)
                .member(memberEntity)
                .build();
    }

    @Test
    @DisplayName("주문 취소 성공")
    void testCancelOrderSuccess() throws GlobalException {
        //given
        Long orderId = 1L;
        String email = "user@example.com";

        //when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(memberEntity));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(memberOrderEntity));

        //then
        OrderCanceled result = orderService.cancelOrder(orderId, email);
        assertEquals(orderId, result.getId());
        verify(orderRepository).save(any(MemberOrderEntity.class));
    }

    @Test
    @DisplayName("존재하지 않은 주문")
    void testCancelOrderWithNonExistentOrder() {
        //given
        Long orderId = 1L;
        String email = "example@example.com";
        //when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(memberEntity));
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        //then
        assertThrows(NullPointerException.class, () -> orderService.cancelOrder(orderId, email));
    }

    @Test
    @DisplayName("잘못된 소유자")
    void testCancelOrderWithWrongOwner() {
        //given
        //given
        Long orderId = 1L;
        String email = "example@example.com";
        MemberEntity differentMember = new MemberEntity();
        //when
        when(memberRepository.findByEmail(email)).thenReturn(Optional.of(differentMember));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(memberOrderEntity));

        //then
        assertThrows(GlobalException.class, () -> orderService.cancelOrder(orderId, email));
    }


}