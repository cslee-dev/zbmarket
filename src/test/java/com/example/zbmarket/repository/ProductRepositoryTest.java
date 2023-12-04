package com.example.zbmarket.repository;

import com.example.zbmarket.repository.entity.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void findProduct() {
        //given
        LocalDateTime now = LocalDateTime.now();
        ProductEntity productEntity = productRepository.save(
                ProductEntity.builder()
                        .id(null)
                        .name("testProduct")
                        .stock(10L)
                        .createdAt(now)
                        .updatedAt(now).build()
        );
        //when
        Optional<ProductEntity> product = productRepository.findById(productEntity.getId());

        //then
        assertTrue(product.isPresent());
        assertEquals(productEntity.getId(), product.get().getId());
        assertEquals(productEntity.getStock(), product.get().getStock());
        assertEquals(productEntity.getName(), product.get().getName());
    }
}