package com.example.zbmarket.service.product;

import com.example.zbmarket.service.product.model.ProductCreated;
import com.example.zbmarket.service.product.model.ProductFound;
import com.example.zbmarket.service.product.model.ProductFoundList;

import java.util.List;

public interface ProductService {
    ProductCreated createProduct(String name, Long stock, Long price);

    ProductFound findProduct(Long id);

    List<ProductFoundList> findProductList();
}
