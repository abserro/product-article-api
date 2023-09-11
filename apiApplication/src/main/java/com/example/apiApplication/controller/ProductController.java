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

@Tag(name = "Endpoints product")
@RestController
@RequestMapping(path = "api/products")
public class ProductController {
    private final ProductService productService;

    ProductController(IProductRepository productRepository, IArticleRepository articleRepository) {
        this.productService = new ProductService(productRepository, articleRepository);
    }

    @Operation(summary = "Create a new Product")
    @PostMapping()
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @Operation(summary = "Get product id")
    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id) {
        ProductEntity productEntity = productService.getProductById(id);
        return ResponseEntity.ok(productEntity);
    }

    @Operation(summary = "Update product by id")
    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProductById(@PathVariable long id, @RequestBody ProductEntity updateProduct) {
        ProductEntity productEntity = productService.updateProductById(id, updateProduct);
        return ResponseEntity.ok(productEntity);
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        return productService.deleteProductById(id) ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    @Operation(summary = "Sorting all products (asc/desc) by field name")
    @GetMapping
    public ResponseEntity<List<ProductEntity>> getProducts(@RequestParam(required = false, defaultValue = "id") String sortField,
                                                           @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        if (!isFieldValid(sortField)) {
            throw new FieldNotFoundException();
        }
        List<ProductEntity> products = productService.getProducts(sortField, sortDirection);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get all article by product_id (asc/desc)")
    @GetMapping("/article/{product_id}")
    public ResponseEntity<List<ArticleEntity>> getAllArticleByProductId(@PathVariable(name = "product_id") long productId,
                                                                        @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        if (!isFieldValid(sortField)) {
            throw new FieldNotFoundException();
        }
        List<ArticleEntity> article = productService.getArticleByProductId(productId, sortField, sortDirection);
        return ResponseEntity.ok(article);
    }

    @Operation(summary = "Get all product by filter cost (min, max)")
    @GetMapping("/cost/range")
    public ResponseEntity<List<ProductEntity>> getAllProductByCostRange(@RequestParam double min,
                                                                        @RequestParam double max,
                                                                        @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        if (!isFieldValid(sortField)) {
            throw new FieldNotFoundException();
        }
        List<ProductEntity> product = productService.getProductByCostRange(min, max, sortField, sortDirection);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Get products by title")
    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<ProductEntity>> getAllProductByTitle(@PathVariable String title,
                                                                    @RequestParam(required = false, defaultValue = "id") String sortField,
                                                                    @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        if (!isFieldValid(sortField)) {
            throw new FieldNotFoundException();
        }
        List<ProductEntity> products = productService.getProductByTitle(title, sortField, sortDirection);
        return ResponseEntity.ok(products);
    }

    private Boolean isFieldValid(String field) {
        List<String> fields = Arrays.asList("id", "title", "description", "cost");
        return fields.contains(field.toLowerCase());
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleProductException(ProductNotFoundException e) {
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The product with this id wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleFieldException(FieldNotFoundException e) {
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The field wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
