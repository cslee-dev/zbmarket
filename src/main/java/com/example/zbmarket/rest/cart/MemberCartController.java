package com.example.zbmarket.rest.cart;

import com.example.zbmarket.rest.cart.model.RequestCartItemCreateDto;
import com.example.zbmarket.rest.cart.model.ResponseCartItemCreateDto;
import com.example.zbmarket.rest.cart.model.ResponseCartItemFoundListDto;
import com.example.zbmarket.service.cart.CartServiceImpl;
import com.example.zbmarket.service.cart.model.CartItemCreated;
import com.example.zbmarket.service.cart.model.CartItemFoundList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class MemberCartController {
    private final CartServiceImpl cartService;

    @PostMapping("/carts/{cartId}/products/{productId}")
    public ResponseCartItemCreateDto createCartItem(
            @PathVariable Long cartId, @PathVariable Long productId,
            @RequestBody RequestCartItemCreateDto requestCartItemCreateDto) {
        CartItemCreated created = cartService.createCartItem(
                cartId, productId, requestCartItemCreateDto.getQuantity()
        );
        return ResponseCartItemCreateDto.from(created);
    }

    @GetMapping("/carts/{cartId}/products")
    public List<ResponseCartItemFoundListDto> findCartItemList(
            @PathVariable Long cartId
    ) {
        List<CartItemFoundList> founds = cartService.findCartItemList(cartId);
        return founds.stream()
                .map(ResponseCartItemFoundListDto::from)
                .collect(Collectors.toList());
    }
}
