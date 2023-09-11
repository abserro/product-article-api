package com.example.apiApplication.controller;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ArticleEntity> updateArticleById(@PathVariable long id, @RequestBody ArticleEntity updateArticle) {
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
}
