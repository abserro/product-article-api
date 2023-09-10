package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Endpoints product")
@RestController
@RequestMapping(path = "api/products/")
public class ProductController {
    private final ProductService productService;

    ProductController(IProductRepository productRepository, IArticleRepository articleRepository) {
        this.productService = new ProductService(productRepository, articleRepository);
    }

    @Operation(summary = "Post Request (new product)")
    @PostMapping()
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @Operation(summary = "Get Request by productID")
    @GetMapping("{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id) {
        ProductEntity productEntity = productService.getProductById(id);
        if (productEntity != null) {
            return ResponseEntity.ok(productEntity);
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Put Request by productID")
    @PutMapping("{id}")
    public ResponseEntity<ProductEntity> updateProductById(@PathVariable long id, @RequestBody ProductEntity updateProduct) {
        ProductEntity productEntity = productService.updateProductById(id, updateProduct);
        if (productEntity != null) {
            return ResponseEntity.ok(productEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete Request by productID")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        if (productService.deleteProductById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
