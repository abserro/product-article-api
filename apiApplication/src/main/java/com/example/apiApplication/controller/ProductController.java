package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ProductService;
import com.example.apiApplication.util.ErrorResponse;
import com.example.apiApplication.util.ProductNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Create a new Product")
    @PostMapping()
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

    @Operation(summary = "Get product id")
    @GetMapping("{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable long id) {
        ProductEntity productEntity = productService.getProductById(id);
        if (productEntity != null) {
            return ResponseEntity.ok(productEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update product by id")
    @PutMapping("{id}")
    public ResponseEntity<ProductEntity> updateProductById(@PathVariable long id, @RequestBody ProductEntity updateProduct) {
        ProductEntity productEntity = productService.updateProductById(id, updateProduct);
        if (productEntity != null) {
            return ResponseEntity.ok(productEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete product by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable long id) {
        if (productService.deleteProductById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Sorting all products (asc/desc) by field name")
    @GetMapping
    public List<ProductEntity> getProducts(@RequestParam(required = false, defaultValue = "id") String sortField,
                                           @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return productService.getProducts(sortField, sortDirection);
    }

    @Operation(summary = "Get all article by product_id (asc/desc)")
    @GetMapping("article/{productId}")
    public List<ArticleEntity> getAllArticleByProductId(@PathVariable long productId,
                                                        @RequestParam(required = false, defaultValue = "id") String sortField,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return productService.getAllArticleByProductId(productId, sortField, sortDirection);
    }

    @Operation(summary = "Get all product by filter cost (min, max)")
    @GetMapping("cost/range")
    public List<ProductEntity> getAllProductByCostRange(@RequestParam double min,
                                                        @RequestParam double max,
                                                        @RequestParam(required = false, defaultValue = "id") String sortField,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return productService.getAllProductByCostRange(min, max, sortField, sortDirection);
    }

    @Operation(summary = "Get products by title")
    @GetMapping("findByTitle/{title}")
    public List<ProductEntity> getAllProductByTitle(@PathVariable String title) {
        return productService.getAllProductByTitle(title);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleProductException(ProductNotFoundException e){
        ErrorResponse response = new ErrorResponse(
                "The product with this id wasn't found!",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
