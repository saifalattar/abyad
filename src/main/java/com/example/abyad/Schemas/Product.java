package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "Products")
@JsonFormat
public class Product {
    @Id
    @JsonProperty
    final private UUID id = UUID.randomUUID();

    @JsonProperty
    @Column
    final private String productName;

    @JsonProperty
    @Column
    final private double price;

    @JsonProperty
    @Column
    final private String color;

    @JsonProperty
    @Column
    final private List<String> images;

    @JsonIgnore
    @Column(name = "users")
    @ManyToMany(mappedBy = "products")
    private Set<User> users = new HashSet<>();

    public Product(){
        this.color = null;
        this.images = null;
        this.productName = null;
        this.price = 0;
    }

    public Product(String productName, String color, double price, List<String> images){
        this.color = color;
        this.images = images;
        this.productName = productName;
        this.price = price;
    }

}
