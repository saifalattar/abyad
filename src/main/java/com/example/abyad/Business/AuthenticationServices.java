package com.example.abyad.Business;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Database.ProductDatabase;
import com.example.abyad.Database.UserDatabase;
import com.example.abyad.Schemas.Product;
import com.example.abyad.Schemas.User;
import com.example.abyad.Shared.SharedFunctions;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class AuthenticationServices {
    final private UserDatabase userDatabase;
    final private AbyadExceptions abyadExceptions;
    final private ProductDatabase productDatabase;
    public AuthenticationServices(UserDatabase userDatabase, ProductDatabase productDatabase, @Qualifier("Exceptions") AbyadExceptions abyadExceptions){
        this.userDatabase = userDatabase;
        this.productDatabase = productDatabase;
        this.abyadExceptions = abyadExceptions;
    }

    public User createUser(User user) throws AbyadExceptions{
        try{
            return userDatabase.save(user);
        } catch (Exception e) {
            if(e.getMessage().contains("already exists")){
                throw abyadExceptions.setErrorKey("UserAlreadyExists");
            }
            throw abyadExceptions.setErrorKey("102025");

        }
    }

    public void addNewProductInCart(String token, UUID userId, UUID productId) throws AbyadExceptions{
        try{
            if (token == null) throw abyadExceptions.setErrorKey("162025");
            if (SharedFunctions.isTokenValid(token)){
                // in case of wrong token
                if(userDatabase.findById(userId).isEmpty()) throw abyadExceptions.setErrorKey("UserNotFound"); // in case of non exists user id

                User user =  userDatabase.findById(userId).get();
                if(productDatabase.findById(productId).isEmpty()) throw abyadExceptions.setErrorKey("172025");
                else{
                    user.getCartProducts().add(productDatabase.findById(productId).get());
                    userDatabase.save(user);
                }

            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("BadRequest");
        }
    }

    public User logIn(User user) throws AbyadExceptions{
        try{
            User foundUser = userDatabase.findFirstByEmailAndPassword(user.getEmail(), user.getPassword());
            if (foundUser != null){
                return foundUser;
            }else{
                User foundEmail = userDatabase.findFirstByEmail(user.getEmail());
                if(foundEmail != null){
                    throw abyadExceptions.setErrorKey("WrongEmailOrPassword");
                }else {
                    throw abyadExceptions.setErrorKey("UserNotFound");
                }
            }
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("112025");
        }
    }
}
