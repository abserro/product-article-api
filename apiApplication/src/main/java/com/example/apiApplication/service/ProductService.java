package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import org.springframework.data.domain.Sort;

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
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product == null)
            return null;
        product.setTitle(updateProduct.getTitle());
        product.setDescription(updateProduct.getDescription());
        product.setCost(updateProduct.getCost());
        return productRepository.save(product);
    }

    @Override
    public List<ProductEntity> getAllProductByCostRange(double min, double max, String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        return productRepository.findByCostBetween(min, max, sort);
    }

    @Override
    public List<ProductEntity> getAllProductByTitle(String title) {
        return productRepository.findByTitle(title);
    }

    @Override
    public List<ArticleEntity> getAllArticleByProductId(long productId, String sortField, String sortDirection) {
        List<ArticleEntity> articles = new ArrayList<>();
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        for (ArticleEntity article : articleRepository.findAll(sort)) {
            if (article.getProductId() == productId)
                articles.add(article);
        }
        return articles;
    }

    @Override
    public List<ProductEntity> getProducts(String sortField, String sortDirection) {
        if (sortDirection != null && sortField != null)
            return null;
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortField);
        return productRepository.findAll(sort);
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
