package com.example.zbmarket.service.cart;

import com.example.zbmarket.service.cart.model.CartItemCreated;
import com.example.zbmarket.service.cart.model.CartItemFoundList;

import java.util.List;

public interface CartItemService {
    CartItemCreated createCartItem(Long cartId, Long productId, Long quantity);

    List<CartItemFoundList> findCartItemList(Long cartId);
}
