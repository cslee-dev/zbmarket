package com.example.zbmarket.service.product;

import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.service.product.model.ProductCreated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 생성할 수 있어야 한다.")
    void createProduct() {
        //given
        LocalDateTime now = LocalDateTime.now();
        String name = "testProduct";
        Long stock = 10L;
        Long price = 100L;
        Mockito.when(
                productRepository.save(
                        Mockito.any()
                )
        ).thenReturn(
                ProductEntity.builder()
                        .id(null)
                        .name(name)
                        .stock(stock)
                        .price(price)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        //when
        ProductCreated productCreated = productService.createProduct(name, stock, price);

        //then
        assertEquals(name, productCreated.getName());
        assertEquals(stock, productCreated.getStock());
    }

}