package com.example.abyad.Controller;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Database.MagazineDatabase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class Configurations {
    @Bean
    @Qualifier("Exceptions")
    public AbyadExceptions abyadExceptions(){
        return new AbyadExceptions();
    }
}
