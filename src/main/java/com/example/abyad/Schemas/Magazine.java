package com.example.abyad.Schemas;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

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
    @NotBlank(message = "name must be sent and not empty.")
    @Size(min = 4, message = "name name must be at least at length of 4.")
    final String name;

    @JsonProperty
    @Column(name = "url")
    @URL(message = "provided url is not correct.")
    @NotBlank(message = "url must be sent and not empty.")
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
