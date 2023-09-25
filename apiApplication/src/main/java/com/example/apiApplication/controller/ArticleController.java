package com.example.apiApplication.controller;

import com.example.apiApplication.ArticleResponseEntity;
import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ArticleService;
import com.example.apiApplication.util.ArticleNotFoundException;
import com.example.apiApplication.util.ErrorResponse;
import com.example.apiApplication.util.FieldNotFoundException;
import com.example.apiApplication.util.ProductNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Tag(name = "Article Endpoints")
@RestController
@RequestMapping(path = "/api/articles")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(IArticleRepository articleRepository, IProductRepository productRepository) {
        this.articleService = new ArticleService(articleRepository, productRepository);
    }

    @Operation(summary = "Create a new article")
    @PostMapping
    public ResponseEntity<Void> createArticle(@RequestBody ArticleEntity article) {
        articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get an article by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseEntity> getArticleById(@PathVariable long id) {
        ArticleEntity articleEntity = articleService.getArticleById(id);
        ArticleResponseEntity articleResponseEntity = new ArticleResponseEntity(articleEntity);
        return ResponseEntity.ok(articleResponseEntity);
    }

    @Operation(summary = "Update an article by ID")
    @PutMapping("/{id}")
    public ResponseEntity<ArticleEntity> updateArticleById(@PathVariable long id, @RequestBody ArticleEntity updateArticle) {
        ArticleEntity articleEntity = articleService.updateArticleById(id, updateArticle);
        return ResponseEntity.ok(articleEntity);
    }

    @Operation(summary = "Delete an article by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable long id) {
        return articleService.deleteArticleById(id) ? ResponseEntity.noContent().build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "Get all articles sorted by a field")
    @GetMapping
    public ResponseEntity<List<ArticleEntity>> getArticles(
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ArticleEntity> articles = articleService.getArticles(sortField, sortDirection);
        return ResponseEntity.ok(articles);
    }

    @Operation(summary = "Get articles by product ID")
    @GetMapping("/products/{productId}")
    public ResponseEntity<List<ArticleEntity>> getArticlesByProductId(
            @PathVariable(name = "productId") long productId,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ArticleEntity> articles = articleService.getArticlesByProductId(productId, sortField, sortDirection);
        return ResponseEntity.ok(articles);
    }

    @Operation(summary = "Get articles by date created")
    @GetMapping("/dateCreated")
    public ResponseEntity<List<ArticleEntity>> getArticlesByDateCreated(
            @RequestParam(required = false, defaultValue = "1970-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateStart,
            @RequestParam(required = false, defaultValue = "3000-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateEnd,
            @RequestParam(required = false, defaultValue = "dateCreated") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        List<ArticleEntity> articles = articleService.getArticlesByDateCreated(dateStart, dateEnd, sortField, sortDirection);
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

    @Operation(summary = "Get articles by title")
    @GetMapping("/findByTitle/{title}")
    public ResponseEntity<List<ArticleEntity>> getArticlesByTitle(
            @PathVariable String title,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        isFieldValid(sortField);
        List<ArticleEntity> articles = articleService.getArticlesByTitle(title, sortField, sortDirection);
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

    @Operation(summary = "Get articles by phrase in description")
    @GetMapping("/findByPhrase/{phrase}")
    public ResponseEntity<List<ArticleEntity>> getArticlesByPhraseContent(
            @PathVariable String phrase,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        if (phrase.isBlank())
            return ResponseEntity.noContent().build();
        List<ArticleEntity> articles = articleService.getArticlesByPhraseContent(phrase, sortField, sortDirection);
        return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
    }

    private void isFieldValid(String field) {
        List<String> fields = Arrays.asList("id", "title", "content", "dateCreated", "productId");
        if (!fields.contains(field))
            throw new FieldNotFoundException();
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleArticleNotFoundException(ArticleNotFoundException e) {
        Date currentDate = new Date();
        ErrorResponse response = new ErrorResponse(
                "The article with this ID wasn't found!",
                currentDate
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
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
