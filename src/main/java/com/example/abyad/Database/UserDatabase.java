package com.example.abyad.Database;

import com.example.abyad.Schemas.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface UserDatabase extends JpaRepository<User, UUID> {
    User findFirstByEmail(String email);
    User findFirstByEmailAndPassword(String email, String password);
}
