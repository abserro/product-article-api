package com.example.apiApplication.repository;

import com.example.apiApplication.entity.ArticleEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IArticleRepository extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findByTitle(String title, Sort sort);

    List<ArticleEntity> findByDateCreatedBetween(Date dateStart, Date dateEnd, Sort sort);
}
