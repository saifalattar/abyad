package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity(name="Magazines")
@JsonFormat
public class Magazine {
    @JsonProperty
    @Id
    @Column(name = "id")
    final UUID id = UUID.randomUUID();

    @JsonProperty
    @Column(name = "name")
    final String name;

    @JsonProperty
    @Column(name = "url")
    final String url;

    public Magazine(){
        this.name = null;
        this.url = null;
    }

    public Magazine(String name, String url){
        this.name = name;
        this.url = url;
    }
}
