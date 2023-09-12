package com.example.apiApplication.service;

import com.example.apiApplication.entity.ArticleEntity;
import com.example.apiApplication.entity.ProductEntity;
import com.example.apiApplication.repository.IArticleRepository;
import com.example.apiApplication.repository.IProductRepository;
import com.example.apiApplication.util.ProductNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

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
    public List<ProductEntity> getProducts(
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Sort sort = createSort(sortField, sortDirection);
        return productRepository.findAll(sort);
    }

    @Override
    public List<ProductEntity> getProductsByCostRange(
            @RequestParam double min,
            @RequestParam double max,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Sort sort = createSort(sortField, sortDirection);
        return productRepository.findByCostBetween(min, max, sort);
    }

    @Override
    public List<ProductEntity> getProductsByTitle(
            @RequestParam String title,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        Sort sort = createSort(sortField, sortDirection);
        return productRepository.findByTitle(title, sort);
    }

    @Override
    public List<ArticleEntity> getArticlesByProductId(
            @PathVariable long productId,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);
        Sort sort = createSort(sortField, sortDirection);
        List<ArticleEntity> articles = new ArrayList<>();
        for (ArticleEntity article : articleRepository.findAll(sort)) {
            if (article.getProduct().getId() == productId)
                articles.add(article);
        }
        return articles;
    }

    @Override
    public List<ProductEntity> getProductsByPhraseDescription(
            @RequestParam String phrase,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {
        List<ProductEntity> products = new ArrayList<>();
        Sort sort = createSort(sortField, sortDirection);
        for (ProductEntity product : productRepository.findAll(sort)) {
            if (product.getDescription().contains(phrase.toLowerCase()))
                products.add(product);
        }
        return products;
    }

    private Sort createSort(String sortField, String sortDirection) {
        return Sort.by(sortDirection.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
    }
}
