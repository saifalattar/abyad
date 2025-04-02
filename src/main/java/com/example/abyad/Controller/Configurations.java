package com.example.abyad.Controller;

import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import com.example.abyad.AbyadExceptions.AbyadExceptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configurations {
    private AbyadErrorMapping abyadErrorMapping;
    Configurations(AbyadErrorMapping abyadErrorMapping){
        this.abyadErrorMapping = abyadErrorMapping;
    }
    @Bean
    @Qualifier("Exceptions")
    public AbyadExceptions abyadExceptions(){
        return new AbyadExceptions(abyadErrorMapping);
    }
}
