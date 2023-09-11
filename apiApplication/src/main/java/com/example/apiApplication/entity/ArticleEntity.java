package com.example.apiApplication.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title cannot be longer than 255 characters")
    private String title;

//    @NotNull(message = "Product id is required")
//    @Column(name = "product_id")
//    private long productId;

    @ManyToOne
    @NotNull(message = "Product id is required")
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ProductEntity product;

    @NotBlank(message = "Content is required")
    private String content;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    public ArticleEntity() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

//    public long getProductId() {
//        return product.getId();
//    }
//
//    public void setProductId(long productId) {
//        this.product.setId(productId);
//    }

    public ProductEntity getProduct() {
        return product;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateCreated() {
        return dateCreated;
    }
}
