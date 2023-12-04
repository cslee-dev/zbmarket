package com.example.zbmarket.service.product;

import com.example.zbmarket.repository.ProductRepository;
import com.example.zbmarket.repository.entity.ProductEntity;
import com.example.zbmarket.service.product.model.ProductCreated;
import com.example.zbmarket.service.product.model.ProductFound;
import com.example.zbmarket.service.product.model.ProductFoundList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Transactional
    @Override
    public ProductCreated createProduct(String name, Long stock) {
        LocalDateTime now = LocalDateTime.now();
        ProductEntity created = productRepository.save(
                ProductEntity.builder()
                        .name(name)
                        .stock(stock)
                        .createdAt(now)
                        .updatedAt(now)
                        .build()
        );

        return ProductCreated.builder()
                .id(created.getId())
                .name(created.getName())
                .stock(created.getStock())
                .createdAt(created.getCreatedAt())
                .updatedAt(created.getUpdatedAt())
                .build();
    }

    @Override
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

    @Override
    public List<ProductFoundList> findProductList() {
        List<ProductEntity> founds = productRepository.findAll();

        return founds.stream()
                .map(ProductFoundList::from)
                .collect(Collectors.toList());
    }

}
