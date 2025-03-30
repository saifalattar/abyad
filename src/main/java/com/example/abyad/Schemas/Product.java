package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    @NotBlank(message = "productName must be sent and not empty.")
    @Size(min = 4, message = "productName name must be at least at length of 4.")
    final private String productName;

    @JsonProperty
    @Column
    @NotNull(message = "price must be sent and not empty.")
    final private double price;

    @JsonProperty
    @Column
    @NotBlank(message = "color must be sent and not empty.")
    @Size(min = 3, message = "color name must be at least at length of 3.")
    final private String color;

    @JsonProperty
    @Column
    @NotNull(message = "images must be sent.")
    final private List<String> images;


    @JsonProperty
    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private Set<Carts> cart;

//    @JsonIgnore
//    @Column(name = "users")
//    @ManyToMany(mappedBy = "products")
//    private Set<User> users = new HashSet<>();

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

    public UUID getId(){
        return id;
    }

}
