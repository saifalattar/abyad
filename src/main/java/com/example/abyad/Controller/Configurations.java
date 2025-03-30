package com.example.abyad.Controller;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {
    @Bean
    @Qualifier("Exceptions")
    public AbyadExceptions abyadExceptions(){
        return new AbyadExceptions();
    }
}
