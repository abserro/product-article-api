package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface IArticleService {
    ArticleEntity createArticle(ArticleEntity article);

    ArticleEntity getArticleById(long id);

    ArticleEntity updateArticleById(long id, ArticleEntity updateArticle);

    boolean deleteArticleById(long id);

    List<ArticleEntity> getArticles(String sortField, String sortDirection);

    List<ArticleEntity> getArticlesByProductId(long productId, String sortField, String sortDirection);

    List<ArticleEntity> getArticlesByDateCreated(Date dateStart, Date dateEnd, String sortField, String sortDirection);

    List<ArticleEntity> getArticlesByTitle(String title, String sortField, String sortDirection);

    List<ArticleEntity> getArticlesByPhraseContent(String phrase, String sortField, String sortDirection);
}
