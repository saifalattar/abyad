package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.*;

@Entity(name = "Users")
@JsonFormat
public class User {
    @Id
    @JsonProperty
    final private UUID id = UUID.randomUUID();

    @JsonProperty
    @Column
    final private String username;

    @JsonProperty
    @Column(unique = true)
    final private String email;

    @JsonProperty
    @Column
    final private String password;

    @JsonProperty
    @Column
    final private String phone;

    @JsonIgnore
    @Column(name = "products")
    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "userID"),
            inverseJoinColumns = @JoinColumn(name = "productID")) // the join table always written in the owner side of the table which will be the user here.
    private Set<Product> products = new HashSet<>();

    public User(){
        this.email=null;
        this.phone=null;
        this.username=null;
        this.password = null;
    }

    public User(String username, String email, String phone, String password){
        this.email=email;
        this.phone=phone;
        this.username=username;
        this.password = password;
    }

    public Set<Product> getCartProducts(){
        return products;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public HashMap<String, String> userClaims(){
        HashMap<String, String> claims = new HashMap<String, String>();
        claims.put("email", this.email);
        claims.put("phone", this.phone);
        claims.put("username", this.username);
        return claims;
    }

}
