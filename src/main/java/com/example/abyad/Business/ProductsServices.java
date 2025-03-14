package com.example.abyad.Business;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Database.ProductDatabase;
import com.example.abyad.Schemas.Product;
import com.example.abyad.Shared.SharedFunctions;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class ProductsServices {
    final private ProductDatabase productDatabase;
    final private AbyadExceptions abyadExceptions;
    public ProductsServices(ProductDatabase productDatabase, @Qualifier("Exceptions") AbyadExceptions abyadExceptions){
        this.productDatabase = productDatabase;
        this.abyadExceptions = abyadExceptions;
    }

    public Product addNewProduct(String token, Product product) throws AbyadExceptions{
        try{
            if(SharedFunctions.isTokenValid(token)){
                return productDatabase.save(product);
            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        }
        catch (Exception e) {
            throw abyadExceptions.setErrorKey("122025");
        }
    }

    public List<Product> getAllProducts(String token) throws AbyadExceptions {
        try{
            if(SharedFunctions.isTokenValid(token)){
                return productDatabase.findAll();
            }else {
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        }
    }


    public List<Product> getProductByName(String productName, String token) throws AbyadExceptions{
        try {
            if (SharedFunctions.isTokenValid(token)) {
                return productDatabase.findByproductNameLike('%' + productName + '%');
            } else {
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("132025");
        }
    }

    public void deleteProductByID(String token , UUID id) throws AbyadExceptions{
        try {
            if (SharedFunctions.isTokenValid(token)) {
                productDatabase.deleteById(id);
            } else {
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        }
        catch (Exception e) {
            throw abyadExceptions.setErrorKey("142025");
        }
    }
}
