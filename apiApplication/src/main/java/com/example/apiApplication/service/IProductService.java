package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface IProductService {
    public ProductEntity getProductById(long id);
    public ProductEntity updateProductById(long id, ProductEntity entity);
    public ProductEntity createProduct(ProductEntity product);
    public Boolean deleteProductById(long id);
    public List<ProductEntity> getAllProductsByIdAsc();
    public List<ProductEntity> getAllProductsByIdDesc();
    public List<ProductEntity> getAllProductsByTitleAsc();
    public List<ProductEntity> getAllProductsByTitleDesc();
    public List<ProductEntity> getAllProductsByCostAsc();
    public List<ProductEntity> getAllProductsByCostDesc();
    public List<ProductEntity> getAllProductByCostRange(double min, double max);
    public List<ProductEntity> getAllProductByTitle(String title);
    public List<ArticleEntity> getAllArticleByProductIdAsc(long productId);
    public List<ArticleEntity> getAllArticleByProductIdDesc(long productId);
}
