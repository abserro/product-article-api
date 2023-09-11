package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.util.ProductNotFoundException;
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
    public ProductEntity createProduct(ProductEntity product) {
        return productRepository.save(product);
    }

    @Override
    public ProductEntity getProductById(long id) {
        return productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductEntity updateProductById(long id, ProductEntity updateProduct) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        product.setTitle(updateProduct.getTitle());
        product.setDescription(updateProduct.getDescription());
        product.setCost(updateProduct.getCost());
        return productRepository.save(product);
    }

    @Override
    public Boolean deleteProductById(long id) {
        ProductEntity product = productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
        productRepository.delete(product);
        return true;
    }

    @Override
    public List<ProductEntity> getProducts(String sortField, String sortDirection) {
        return productRepository.findAll(getSort(sortField, sortDirection));
    }

    @Override
    public List<ProductEntity> getProductByCostRange(double min, double max, String sortField, String sortDirection) {
        return productRepository.findByCostBetween(min, max, getSort(sortField, sortDirection));
    }

    @Override
    public List<ProductEntity> getProductByTitle(String title, String sortField, String sortDirection) {
        return productRepository.findByTitle(title, getSort(sortField, sortDirection));
    }

    @Override
    public List<ArticleEntity> getArticleByProductId(long productId, String sortField, String sortDirection) {
        List<ArticleEntity> articles = new ArrayList<>();
        for (ArticleEntity article : articleRepository.findAll(getSort(sortField, sortDirection))) {
            if (article.getProduct().getId() == productId)
                articles.add(article);
        }
        return articles;
    }

    @Override
    public List<ProductEntity> getProductByPhraseDescription(String phrase, String sortField, String sortDirection) {
        List<ProductEntity> products = new ArrayList<>();
        for (ProductEntity product : productRepository.findAll(getSort(sortField, sortDirection))) {
            if (product.getDescription().contains(phrase.toLowerCase()))
                products.add(product);
        }
        return products;
    }

    private Sort getSort(String sortField, String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(direction, sortField);
    }
}
