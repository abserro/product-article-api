package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ArticleService;
import com.example.apiApplication.util.ArticleNotFoundException;
import com.example.apiApplication.util.ErrorResponse;
import com.example.apiApplication.util.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Endpoints articles")
@RestController
@RequestMapping(path = "api/articles/")
public class ArticleController {
    private final ArticleService articleService;
    
    public ArticleController(IArticleRepository articleRepository, IProductRepository productRepository) {

        this.articleService = new ArticleService(articleRepository, productRepository);
    }

    @Operation(summary = "Create a new Article")
    @PostMapping()
    public ArticleEntity createArticle(@RequestBody ArticleEntity article) {
        return articleService.createArticle(article);
    }

    @Operation(summary = "Get article id")
    @GetMapping("{id}")
    public ResponseEntity<ArticleEntity> getArticleById(@PathVariable long id) {
        ArticleEntity articleEntity = articleService.getArticleById(id);
        if (articleEntity != null) {
            return ResponseEntity.ok(articleEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Update article by id")
    @PutMapping("{id}")
    public ResponseEntity<ArticleEntity> updateArticleById(@PathVariable long id,
                                                           @RequestBody ArticleEntity updateArticle) {
        ArticleEntity articleEntity = articleService.updateArticleById(id, updateArticle);
        if (articleEntity != null) {
            return ResponseEntity.ok(articleEntity);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete article by id")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable long id) {
        if (articleService.deleteArticleById(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Sorting all articles (asc/desc) by field name")
    @GetMapping
    public List<ArticleEntity> getArticles(@RequestParam(required = false, defaultValue = "id") String sortField,
                                           @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return articleService.getArticles(sortField, sortDirection);
    }

    @Operation(summary = "Get articles (asc/desc) by productId")
    @GetMapping("products/{product_id}")
    public List<ArticleEntity> getArticlesByProductId(@PathVariable(name = "product_id") long productId,
                                                      @RequestParam(required = false, defaultValue = "id") String sortField,
                                                      @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return articleService.getArticlesByProductId(productId, sortField, sortDirection);
    }

    @Operation(summary = "Get articles (asc/desc) by date created")
    @GetMapping("date_create")
    public List<ArticleEntity> getArticlesByDateCreated(@RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
                                                        @RequestParam(required = false, defaultValue = "3000-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd,
                                                        @RequestParam(required = false, defaultValue = "id") String sortField,
                                                        @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        return articleService.getArticlesByDateCreated(dateStart, dateEnd, sortField, sortDirection);
    }

    @Operation(summary = "Get articles (asc/desc) by title")
    @GetMapping("findByTitle/{title}")
    public List<ArticleEntity> getArticlesByTitle(@PathVariable() String title,
                                                  @RequestParam(required = false, defaultValue = "id") String sortField,
                                                  @RequestParam(required = false, defaultValue = "asc") String sortDirection){
        return articleService.getArticlesByTitle(title, sortField, sortDirection);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleProductException(ArticleNotFoundException e){
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The article with this id wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleProductException(ProductNotFoundException e){
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The product with this id wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
