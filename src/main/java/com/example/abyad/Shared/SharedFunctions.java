package com.example.abyad.Shared;
import com.example.abyad.Schemas.User;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.beans.ConstructorProperties;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SharedFunctions {
    public static String generateToken(User user){
        return Jwts.builder()
                .setClaims(user.userClaims())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10)).compact(); // token available for 10 days
    }
    public static Jwt decodeToken(String token){
        return Jwts.parserBuilder().build().parse(token);
    }
    public static boolean isTokenValid(String token){
        try {
            Jwt decoded = decodeToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            return false;
        }
    }
}
