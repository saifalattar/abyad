package com.example.abyad.AbyadExceptions;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@ConfigurationProperties("errors")
public record AbyadErrorMapping(Map<String, Error> error) {
}
