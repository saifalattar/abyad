package com.example.abyad.Database;

import com.example.abyad.Schemas.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductDatabase extends JpaRepository<Product, UUID> {
    List<Product> findByproductNameLike(String productName);
}
