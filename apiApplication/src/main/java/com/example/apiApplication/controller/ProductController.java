package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.IProductService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.Tag;
import com.example.apiApplication.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "api/products/")
public class ProductController {
    private ProductService productService;

    ProductController(IProductRepository productRepository, IArticleRepository articleRepository) {
        this.productService = new ProductService(productRepository, articleRepository);
    }

    @PostMapping()
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return productService.createProduct(product);
    }

//    @GetMapping("/show")
////    @ApiOperation("новый метод")
//    public ResponseEntity getProduct() {
//        try {
//            return ResponseEntity.ok("work!!!");
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("dont :((((");
//        }
////        return ResponseEntity.ok("work!!!");
//    }

//    @GetMapping
//    @ApiOperation("новый метод2")
//    public ResponseEntity createProduct() {
//
//    }

}
