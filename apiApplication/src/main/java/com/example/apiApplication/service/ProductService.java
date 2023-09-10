package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;

import java.util.List;

public class ProductService implements IProductService {
    private final IProductRepository productRepository;
    private final IArticleRepository articleRepository;

    public ProductService(IProductRepository productRepository, IArticleRepository articleRepository) {
        this.productRepository = productRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public ProductEntity getProductById(long id) {
        return null;
    }

    @Override
    public ProductEntity getProductsAsc() {
        return null;
    }

    @Override
    public ProductEntity getProductsDesc() {
        return null;
    }

    @Override
    public ProductEntity getProductByName(String name) {
        return null;
    }

    @Override
    public ProductEntity getProductByCost(Double cost) {
        return null;
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(ProductEntity product) {
        return null;
    }

    @Override
    public Boolean deleteProductById(long id) {
        return null;
    }

    @Override
    public List<ArticleEntity> getArticleByProductIdDesc(long id) {
        return null;
    }

    @Override
    public List<ArticleEntity> getArticleByProductIdAsc(long id) {
        return null;
    }

    @Override
    public List<ArticleEntity> getProductNoneArticle() {
        return null;
    }

    @Override
    public List<ArticleEntity> getProductsByCostAsc() {
        return null;
    }

    @Override
    public List<ArticleEntity> getProductsByCostDesc() {
        return null;
    }
}
