package com.example.apiApplication.repository;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByOrderByIdAsc();
    List<ProductEntity> findByOrderByIdDesc();
    List<ProductEntity> findByOrderByTitleDesc();
    List<ProductEntity> findByOrderByTitleAsc();
    List<ProductEntity> findByOrderByCostAsc();
    List<ProductEntity> findByOrderByCostDesc();
    List<ProductEntity> findByCostBetween(double min, double max);
    List<ProductEntity> findByTitle(String title);

}
