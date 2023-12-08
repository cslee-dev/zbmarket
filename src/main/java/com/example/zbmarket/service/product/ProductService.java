package com.example.zbmarket.service.product;

import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.service.product.model.ProductCreated;
import com.example.zbmarket.service.product.model.ProductFound;
import com.example.zbmarket.service.product.model.ProductFoundList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductCreated createProduct(String name, Long stock, Long price) {
        LocalDateTime now = LocalDateTime.now();
        ProductEntity created = productRepository.save(
                ProductEntity.builder()
                        .name(name)
                        .stock(stock)
                        .price(price)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        return ProductCreated.builder()
                .id(created.getId())
                .name(created.getName())
                .stock(created.getStock())
                .price(created.getPrice())
                .createdAt(created.getCreatedAt())
                .updatedAt(created.getUpdatedAt())
                .build();
    }


    public ProductFound findProduct(Long id) {
        ProductEntity found = productRepository.findById(id).orElseThrow(
                NullPointerException::new
        );

        return ProductFound.builder()
                .id(found.getId())
                .name(found.getName())
                .stock(found.getStock())
                .createdAt(found.getCreatedAt())
                .updatedAt(found.getUpdatedAt())
                .build();

    }


    public List<ProductFoundList> findProductList() {
        List<ProductEntity> founds = productRepository.findAll();

        return founds.stream()
                .map(ProductFoundList::from)
                .collect(Collectors.toList());
    }

}
