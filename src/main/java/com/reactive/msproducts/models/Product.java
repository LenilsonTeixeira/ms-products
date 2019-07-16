package com.reactive.msproducts.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Product {

    @Id
    private String productId;

    private String description;

    public Product(Product product, String productId){
        this.productId = productId;
        this.description = product.getDescription();

    }
}
