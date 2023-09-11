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
    public List<ProductEntity> getAllProductByCostRange(double min, double max, String sortField, String sortDirection);
    public List<ProductEntity> getAllProductByTitle(String title, String sortField, String sortDirection);
    public List<ArticleEntity> getAllArticleByProductId(long productId, String sortField, String sortDirection);
    public List<ProductEntity> getProducts(String sortField, String sortDirection);
}
