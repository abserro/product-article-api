package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;

import java.util.ArrayList;
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
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public ProductEntity updateProductById(long id, ProductEntity updateProduct) {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if (productEntity != null) {
            productEntity.setTitle(updateProduct.getTitle());
            productEntity.setDescription(updateProduct.getDescription());
            productEntity.setCost(updateProduct.getCost());
            return productRepository.save(productEntity);
        }
        return null;
    }

    @Override
    public List<ProductEntity> getAllProductsByIdAsc() {
        return productRepository.findByOrderByIdAsc();
    }

    @Override
    public List<ProductEntity> getAllProductsByIdDesc() {
        return productRepository.findByOrderByIdDesc();
    }

    @Override
    public List<ProductEntity> getAllProductsByTitleAsc() {
        return productRepository.findByOrderByTitleAsc();
    }

    @Override
    public List<ProductEntity> getAllProductsByTitleDesc() {
        return productRepository.findByOrderByTitleDesc();
    }

    @Override
    public List<ProductEntity> getAllProductsByCostAsc() {
        return productRepository.findByOrderByCostAsc();
    }

    @Override
    public List<ProductEntity> getAllProductsByCostDesc() {
        return productRepository.findByOrderByCostDesc();
    }

    @Override
    public List<ProductEntity> getAllProductByCostRange(double min, double max) {
        return productRepository.findByCostBetween(min, max);
    }

    @Override
    public List<ProductEntity> getAllProductByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    public List<ArticleEntity> getAllArticleByProductIdAsc(long productId) {
        List<ArticleEntity> articles = new ArrayList<>();
        for (ArticleEntity article : articleRepository.findByOrderByIdAsc()) {
            if (article.getProductId() == productId) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public List<ArticleEntity> getAllArticleByProductIdDesc(long productId) {
        List<ArticleEntity> articles = new ArrayList<>();
        for (ArticleEntity article : articleRepository.findByOrderByIdDesc()) {
            if (article.getProductId() == productId) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProductById(long id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null) {
            productRepository.delete(product);
            return true;
        }
        return false;
    }
}
