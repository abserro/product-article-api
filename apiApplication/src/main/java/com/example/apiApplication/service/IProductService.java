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
    public ProductEntity getProductsAsc();
    public ProductEntity getProductsDesc();
    public ProductEntity getProductByName(String name);
    public ProductEntity getProductByCost(Double cost);
    public List<ArticleEntity> getArticleByProductIdDesc(long id);
    public List<ArticleEntity> getArticleByProductIdAsc(long id);
    public List<ArticleEntity> getProductNoneArticle(); //?
    public List<ArticleEntity> getProductsByCostAsc();
    public List<ArticleEntity> getProductsByCostDesc();
}
