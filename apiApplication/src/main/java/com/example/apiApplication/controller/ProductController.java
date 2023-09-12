package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ProductService;
import com.example.apiApplication.util.ErrorResponse;
import com.example.apiApplication.util.FieldNotFoundException;
import com.example.apiApplication.util.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Tag(name = "Product Endpoints")
@RestController
@RequestMapping(path = "/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(IProductRepository productRepository, IArticleRepository articleRepository) {
        this.productService = new ProductService(productRepository, articleRepository);
    }

    @Operation(summary = "Create a new product")
    @PostMapping()
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ProductEntity createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @Operation(summary = "Get a product by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id) {
        ProductEntity productEntity = productService.getProductById(id);
        return ResponseEntity.ok(productEntity);
    }

    @Operation(summary = "Update a product by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProductById(@PathVariable long id, @RequestBody ProductEntity updateProduct) {
        ProductEntity updatedProduct = productService.updateProductById(id, updateProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @Operation(summary = "Delete a product by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        return productService.deleteProductById(id) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get all products sorted by a field")
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getProducts(
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ProductEntity> products = productService.getProducts(sortField, sortDirection);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get all articles by product ID (asc/desc)")
    @GetMapping("/article/{productId}")
    public ResponseEntity<List<ArticleEntity>> getArticlesByProductId(
            @PathVariable(name = "productId") long productId,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ArticleEntity> articles = productService.getArticlesByProductId(productId, sortField, sortDirection);
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

    @Operation(summary = "Get all products by cost range")
    @GetMapping("/cost/range")
    public ResponseEntity<List<ProductEntity>> getProductsByCostRange(
            @RequestParam double min,
            @RequestParam double max,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ProductEntity> products = productService.getProductsByCostRange(min, max, sortField, sortDirection);
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    @Operation(summary = "Get products by title")
    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<ProductEntity>> getProductsByTitle(
            @PathVariable String title,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ProductEntity> products = productService.getProductsByTitle(title, sortField, sortDirection);
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    @Operation(summary = "Get products by phrase in description")
    @GetMapping("/findByPhrase/{phrase}")
    public ResponseEntity<List<ProductEntity>> getProductsByPhraseDescription(
            @PathVariable String phrase,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        if (phrase.isBlank())
            return ResponseEntity.noContent().build();
        List<ProductEntity> products = productService.getProductsByPhraseDescription(phrase, sortField, sortDirection);
        return products.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(products);
    }

    private void isFieldValid(String field) {
        List<String> fields = Arrays.asList("id", "title", "description", "cost");
        if (!fields.contains(field.toLowerCase()))
            throw new FieldNotFoundException();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleProductNotFoundException(ProductNotFoundException e) {
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The product with this ID wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleFieldNotFoundException(FieldNotFoundException e) {
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The field wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}

