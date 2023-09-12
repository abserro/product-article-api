package com.example.apiApplication.repository;

import com.example.apiApplication.entity.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByCostBetween(double min, double max, Sort sort);

    List<ProductEntity> findByTitle(String title, Sort sort);
}
