package com.example.apiApplication.entity;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
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

    @ManyToOne()
    @NotNull(message = "Product id is required")
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity product;

    @NotBlank(message = "Content is required")
    private String content;

    @CreationTimestamp
    @Column(name = "date_created")
    private Date dateCreated;

    public ArticleEntity() {

    }

    public ArticleEntity(String title, String content, ProductEntity product) {
        this.title = title;
        this.content = content;
        this.product = product;
        this.dateCreated = new Date();
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

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
