package com.example.zbmarket.service.cart;

import com.example.zbmarket.repository.CartItemRepository;
import com.example.zbmarket.repository.MemberCartRepository;
import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.CartItemEntity;
import com.example.zbmarket.repository.entity.MemberCartEntity;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.service.cart.model.CartItemCreated;
import com.example.zbmarket.service.cart.model.CartItemFoundList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final MemberCartRepository memberCartRepository;

    @Transactional
    @Override
    public CartItemCreated createCartItem(Long cartId, Long productId, Long quantity) {
        LocalDateTime now = LocalDateTime.now();
        MemberCartEntity memberCartEntity = memberCartRepository
                .findById(cartId).orElseThrow(NullPointerException::new);
        ProductEntity productEntity = productRepository
                .findById(productId).orElseThrow(NullPointerException::new);

        CartItemEntity saved = cartItemRepository.save(
                CartItemEntity.builder()
                        .id(null)
                        .cart(memberCartEntity)
                        .product(productEntity)
                        .productName(productEntity.getName())
                        .quantity(quantity)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        return CartItemCreated.builder()
                .id(saved.getId())
                .cartId(saved.getCart().getId())
                .productId(saved.getProduct().getId())
                .productName(saved.getProductName())
                .quantity(saved.getQuantity())
                .build();
    }

    @Override
    public List<CartItemFoundList> findCartItemList(Long cartId) {
        List<CartItemEntity> founds = cartItemRepository.findByCartId(cartId);
        return founds.stream()
                .map(CartItemFoundList::from)
                .collect(Collectors.toList());
    }
}
