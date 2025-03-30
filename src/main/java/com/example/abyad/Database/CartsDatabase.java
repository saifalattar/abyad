package com.example.abyad.Database;

import com.example.abyad.Schemas.Carts;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartsDatabase extends JpaRepository<Carts, String> { }
