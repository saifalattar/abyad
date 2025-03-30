package com.example.abyad;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("properties")
public record Properties(Map<String, String> property) {
}
