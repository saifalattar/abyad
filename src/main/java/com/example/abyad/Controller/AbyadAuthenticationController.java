package com.example.abyad.Controller;
import com.example.abyad.AbyadExceptions.AbyadErrorMapping;
import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Business.AuthenticationServices;
import com.example.abyad.Schemas.User;
import com.example.abyad.Shared.SharedFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1.0.0/")
public class AbyadAuthenticationController {
    @Autowired
    private AbyadErrorMapping abyadErrorMapping;
    final private AuthenticationServices services;

    public AbyadAuthenticationController(AuthenticationServices services){
        this.services = services;
    }

    @PostMapping(path = "/CreateAccount")
    public ResponseEntity createNewUser(@RequestBody User user){
        try {
            HashMap<String, Object> response = new HashMap<String, Object>();
            String token = SharedFunctions.generateToken(services.createUser(user));
            response.put("userData", user);
            response.put("status", "201");
            response.put("token", token);
            return new ResponseEntity(response, HttpStatus.CREATED);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @PostMapping(path = "/LogIn")
    public ResponseEntity logIn(@RequestBody User user){
        try {
            System.out.println(abyadErrorMapping.error().get("first"));
            User foundUser = services.logIn(user);
            HashMap<String, Object> response = new HashMap<String, Object>();
            String token = SharedFunctions.generateToken(foundUser);
            response.put("status", "200");
            response.put("userData", foundUser);
            response.put("token", token);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @PutMapping("/{userId}/cart/{productId}")
    public ResponseEntity<HashMap<String, Object>> addNewCartItem(
            @RequestHeader String token,
            @PathVariable UUID userId,
            @PathVariable UUID productId){
        try{
            services.addNewProductInCart(token, userId, productId);
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put("status", "201");
            response.put("description", "Added new cart item successfully.");
            return new ResponseEntity(response, HttpStatus.CREATED);

        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }
}
