package com.example.abyad.Controller;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Business.AuthenticationServices;
import com.example.abyad.Business.ProductsServices;
import com.example.abyad.Schemas.Product;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1.0.0/products")
public class AbyadProductsController {

    final private ProductsServices services;

    public AbyadProductsController(ProductsServices services){
        this.services = services;
    }

    @GetMapping("/")
    public ResponseEntity<HashMap<String , Object>> getAllProducts(@RequestHeader String token){
        try {
            HashMap<String , Object> response = new HashMap<String, Object>();
            response.put("products", services.getAllProducts(token));
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @PostMapping("/AddNewProduct")
    public ResponseEntity<HashMap<String, Object>> addNewProduct(@Valid @RequestBody Product product, @RequestHeader String token){
        try {
            HashMap<String, Object> responseBody = new HashMap<String, Object>();
            responseBody.put("status", "201");
            responseBody.put("description", "Product added successfully");
            responseBody.put("Product", services.addNewProduct(token, product));
            return new ResponseEntity<HashMap<String, Object>>(responseBody, HttpStatus.CREATED);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @GetMapping("/{productName}")
    public ResponseEntity<HashMap<String , Object>> getProductsByName(@PathVariable String productName, @RequestHeader String token){
        try {
            HashMap<String , Object> responseBody = new HashMap<String, Object>();
            responseBody.put("foundProducts", services.getProductByName(productName, token));
            return new ResponseEntity<HashMap<String , Object>>(responseBody, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @DeleteMapping("/{productID}")
    public ResponseEntity<HashMap<String, Object>> deleteProductByID(@PathVariable UUID productID, @RequestHeader String token){
        try {
            services.deleteProductByID(token, productID);
            HashMap<String , Object> response = new HashMap<String, Object>();
            response.put("status", "200");
            response.put("description", "Product deleted successfully.");
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }
}
