package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    ProductEntity getProductById(long id);

    ProductEntity updateProductById(long id, ProductEntity entity);

    ProductEntity createProduct(ProductEntity product);

    Boolean deleteProductById(long id);
    List<ProductEntity> getProducts(String sortField, String sortDirection);

    List<ProductEntity> getProductByCostRange(double min, double max, String sortField, String sortDirection);

    List<ProductEntity> getProductByTitle(String title, String sortField, String sortDirection);

    List<ArticleEntity> getArticleByProductId(long productId, String sortField, String sortDirection);

    List<ProductEntity> getProductByPhraseDescription(String phrase, String sortField, String sortDirection);
}
