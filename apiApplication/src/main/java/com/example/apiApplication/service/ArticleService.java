package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.util.ArticleNotFoundException;
import com.example.apiApplication.util.ProductNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleService implements IArticleService {
    private final IArticleRepository articleRepository;
    private final IProductRepository productRepository;

    public ArticleService(IArticleRepository articleRepository, IProductRepository productRepository) {
        this.articleRepository = articleRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ArticleEntity createArticle(ArticleEntity article) {
        productRepository.findById(article.getProduct().getId()).orElseThrow(ProductNotFoundException::new);
        return articleRepository.save(article);
    }

    @Override
    public ArticleEntity getArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public ArticleEntity updateArticleById(long id, ArticleEntity updateArticle) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        ProductEntity product = productRepository.findById(updateArticle.getProduct().getId()).orElseThrow(ProductNotFoundException::new);
        article.setTitle(updateArticle.getTitle());
        article.setContent(updateArticle.getContent());
        article.getProduct().setId(product.getId());
        return articleRepository.save(article);
    }

    @Override
    public boolean deleteArticleById(long id) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        articleRepository.delete(article);
        return true;
    }

    @Override
    public List<ArticleEntity> getArticles(String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        return articleRepository.findAll(sort);
    }

    @Override
    public List<ArticleEntity> getArticlesByProductId(long productId, String sortField, String sortDirection) {
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        List<ArticleEntity> articles = new ArrayList<>();
        for (ArticleEntity article : articleRepository.findAll(sort))
            if (article.getProduct().getId() == productId)
                articles.add(article);
        return articles;
    }

    @Override
    public List<ArticleEntity> getArticlesByDateCreated(Date min, Date max, String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        return articleRepository.findByDateCreatedBetween(min, max, sort);
    }

    @Override
    public List<ArticleEntity> getArticlesByTitle(String title, String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        return articleRepository.findByTitle(title, sort);
    }
}
