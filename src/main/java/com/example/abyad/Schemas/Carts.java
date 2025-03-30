package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.validator.constraints.UUID;
import org.springframework.context.annotation.Primary;

@JsonFormat
@Entity
public class Carts {
    @Id
    @JsonProperty
    @Column
    private String id;

    @Column
    @JsonProperty
    private int quantity;

    @JsonProperty
    @ManyToOne
    @JoinColumn(name = "product_id")
    final private Product product;

    @ManyToOne
    @JsonProperty
    @JoinColumn(name = "user_id")
    @JsonIgnore
    final private User user;


    public Carts(int quantity, Product product, User user) {
        this.quantity = quantity;
        this.product = product;
        this.user = user;
        this.id = user.getId()+product.getId().toString();
    }

    public Carts(){
        this.id = "xxxxxxx";
        this.quantity = 1;
        this.product = new Product();
        this.user = new User();
    }

    public Carts setQuatity(int quantity){
        this.quantity = quantity;
        return this;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String getId(){
        return this.id;
    }
}
