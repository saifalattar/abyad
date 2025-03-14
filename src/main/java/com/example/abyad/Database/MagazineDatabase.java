package com.example.abyad.Database;

import com.example.abyad.Schemas.Magazine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MagazineDatabase extends JpaRepository<Magazine, UUID> {
    List<Magazine> findByNameLike(String Name);
}
