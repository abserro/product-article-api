package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.util.ArticleNotFoundException;
import org.springframework.data.domain.Sort;

import java.util.List;

public class ArticleService implements IArticleService {
    private final IArticleRepository articleRepository ;
    private final IProductRepository productRepository;

    public ArticleService(IArticleRepository articleRepository, IProductRepository productRepository) {
        this.articleRepository = articleRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ArticleEntity createArticle(ArticleEntity article) {
        return articleRepository.save(article);
    }

    @Override
    public ArticleEntity getArticleById(long id) {
        return articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
    }

    @Override
    public ArticleEntity updateArticleById(long id, ArticleEntity updateArticle) {
        ArticleEntity article = articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        article.setTitle(updateArticle.getTitle());
        article.setContent(updateArticle.getContent());
        article.setProductId(updateArticle.getProductId());
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
}
