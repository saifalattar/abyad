package com.example.abyad.Business;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Database.CartsDatabase;
import com.example.abyad.Database.ProductDatabase;
import com.example.abyad.Database.UserDatabase;
import com.example.abyad.Schemas.Carts;
import com.example.abyad.Schemas.Product;
import com.example.abyad.Schemas.User;
import com.example.abyad.Schemas.UserLoginRequestDTO;
import com.example.abyad.Shared.SharedFunctions;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthenticationServices {
    final private UserDatabase userDatabase;
    final private AbyadExceptions abyadExceptions;
    final private ProductDatabase productDatabase;
    final private CartsDatabase cartsDatabase;

    public AuthenticationServices(UserDatabase userDatabase, ProductDatabase productDatabase, CartsDatabase cartsDatabase, @Qualifier("Exceptions") AbyadExceptions abyadExceptions){
        this.userDatabase = userDatabase;
        this.productDatabase = productDatabase;
        this.abyadExceptions = abyadExceptions;
        this.cartsDatabase = cartsDatabase;
    }

    public User createUser(User user) throws AbyadExceptions{
        try{
            return userDatabase.save(user);
        } catch (Exception e) {
            if(e.getMessage().contains("already exists")){
                throw abyadExceptions.setErrorKey("UserAlreadyExists");
            }
            System.out.println(e);
            throw abyadExceptions.setErrorKey("102025");

        }
    }

    public void modifyingCartItems(boolean isAdding, String token, UUID userId, UUID productId) throws AbyadExceptions{
        try{
            if (token == null || token.isEmpty()) throw abyadExceptions.setErrorKey("162025");
            if (SharedFunctions.isTokenValid(token)){
                // in case of wrong token
                User user;
                Product product;
                try {
                    user = userDatabase.findById(userId).get();
                } catch (NoSuchElementException e) {
                    throw abyadExceptions.setErrorKey("UserNotFound");
                }
                try{
                    product = productDatabase.findById(productId).get();
                } catch (Exception e) {
                    throw abyadExceptions.setErrorKey("172025");
                }

                Optional<Carts> foundItem = user.getCartProducts().stream().filter(cartItem -> cartItem.getId().equals(user.getId() + product.getId().toString())).findFirst();

                if(isAdding){
                    if(foundItem.isPresent()){
                        cartsDatabase.save(foundItem.get().setQuatity(foundItem.get().getQuantity() + 1));
                    }else {
                        Carts cartItem = new Carts(1, product, user);
                        cartsDatabase.save(cartItem);
                    }
                }else{
                    if(foundItem.isEmpty()){
                        throw abyadExceptions.setErrorKey("192025");
                    }
                    else if(foundItem.get().getQuantity() > 1){
                        cartsDatabase.save(foundItem.get().setQuatity(foundItem.get().getQuantity() - 1));
                    }else if(foundItem.get().getQuantity() == 1) {
                        cartsDatabase.deleteById(user.getId() + product.getId().toString());
                    }
                }

            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            System.out.println(e);
            throw abyadExceptions.setErrorKey("182025");
        }
    }

    public User logIn(UserLoginRequestDTO user) throws AbyadExceptions{
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

    public User getUserDetails(String token) throws AbyadExceptions {
        if (token == null || token.isEmpty()) throw abyadExceptions.setErrorKey("162025");
        try{
            DefaultClaims user = (DefaultClaims)SharedFunctions.decodeToken(token).getBody();
            return userDatabase.findById(UUID.fromString(user.get("id").toString())).get();
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("UnAuthorized");
        }
    }
}
