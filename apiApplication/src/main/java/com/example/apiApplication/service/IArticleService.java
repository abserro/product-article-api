package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IArticleService {
    ArticleEntity createArticle(ArticleEntity article);

    ArticleEntity getArticleById(long id);

    ArticleEntity updateArticleById(long id, ArticleEntity updateArticle);

    boolean deleteArticleById(long id);

    List<ArticleEntity> getArticles(String sortField, String sortDirection);
}
