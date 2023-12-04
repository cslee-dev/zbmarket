package com.example.zbmarket.rest.product;

import com.example.zbmarket.rest.product.model.RequestProductCreateDto;
import com.example.zbmarket.rest.product.model.ResponseProductCreateDto;
import com.example.zbmarket.rest.product.model.ResponseProductFoundDto;
import com.example.zbmarket.rest.product.model.ResponseProductFoundListDto;
import com.example.zbmarket.service.product.ProductServiceImpl;
import com.example.zbmarket.service.product.model.ProductCreated;
import com.example.zbmarket.service.product.model.ProductFound;
import com.example.zbmarket.service.product.model.ProductFoundList;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/products")
    public ResponseProductCreateDto createProduct(@RequestBody RequestProductCreateDto requestProductCreateDto) {
        ProductCreated created = productService.createProduct(
                requestProductCreateDto.getName(),
                requestProductCreateDto.getStock()
        );
        return ResponseProductCreateDto.from(created);
    }

    @GetMapping("/products/{productId}")
    public ResponseProductFoundDto findProduct(@PathVariable Long productId) {
        ProductFound found = productService.findProduct(productId);
        return ResponseProductFoundDto.from(found);
    }

    @GetMapping("/products")
    public List<ResponseProductFoundListDto> findProductList() {
        List<ProductFoundList> founds = productService.findProductList();
        return founds.stream()
                .map(ResponseProductFoundListDto::from)
                .collect(Collectors.toList());
    }
}
