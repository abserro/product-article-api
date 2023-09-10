package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;

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

    @Operation(summary = "Get all products by id asc")
    @GetMapping("id/asc")
    public List<ProductEntity> getAllProductsByIdAsc() {
        return productService.getAllProductsByIdAsc();
    }

    @Operation(summary = "Get all products by id desc")
    @GetMapping("id/desc")
    public List<ProductEntity> getAllProductsByIdDesc() {
        return productService.getAllProductsByIdDesc();
    }

    @Operation(summary = "Get all products by title asc")
    @GetMapping("title/asc")
    public List<ProductEntity> getAllProductsByTitleAsc() {
        return productService.getAllProductsByTitleAsc();
    }

    @Operation(summary = "Get all products by title asc")
    @GetMapping("title/desc")
    public List<ProductEntity> getAllProductsByTitleDesc() {
        return productService.getAllProductsByTitleDesc();
    }

    @Operation(summary = "Get all products by cost asc")
    @GetMapping("cost/asc")
    public List<ProductEntity> getAllProductsByCostAsc() {
        return productService.getAllProductsByCostAsc();
    }

    @Operation(summary = "Get all products by cost asc")
    @GetMapping("cost/desc")
    public List<ProductEntity> getAllProductsByCostDesc() {
        return productService.getAllProductsByCostDesc();
    }

    @Operation(summary = "Get all article by product_id asc")
    @GetMapping("article/{productId}/asc")
    public List<ArticleEntity> getAllArticleByProductIdAsc(@PathVariable long productId) {
        return productService.getAllArticleByProductIdAsc(productId);
    }

    @Operation(summary = "Get all article by product_id desc")
    @GetMapping("article/{productId}/desc")
    public List<ArticleEntity> getAllArticleByProductIdDesc(@PathVariable long productId) {
        return productService.getAllArticleByProductIdDesc(productId);
    }

    @Operation(summary = "Get all product by filter cost (min, max)")
    @GetMapping("cost-range")
    public List<ProductEntity> getAllProductByCostRange(@RequestParam double min, @RequestParam double max) {
        return productService.getAllProductByCostRange(min, max);
    }

    @GetMapping("findByTitle/{title}")
    public List<ProductEntity> getAllProductByTitle(@PathVariable String title){
        return productService.getAllProductByTitle(title);
    }
}
