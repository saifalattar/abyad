package com.example.abyad.Controller;
import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Business.AuthenticationServices;
import com.example.abyad.Schemas.User;
import com.example.abyad.Schemas.UserLoginRequestDTO;
import com.example.abyad.Shared.SharedFunctions;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1.0.0/")
public class AbyadAuthenticationController {
    final private AuthenticationServices services;

    public AbyadAuthenticationController(AuthenticationServices services){
        this.services = services;
    }

    @PostMapping(path = "/CreateAccount")
    public ResponseEntity<HashMap<String, Object>> createNewUser(@Valid @RequestBody User user){
        try {
            HashMap<String, Object> response = new HashMap<String, Object>();
            String token = SharedFunctions.generateToken(services.createUser(user));
            response.put("userData", user.toUserResponseDTO());
            response.put("status", "201");
            response.put("token", token);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.CREATED);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @GetMapping("/test")
    public Map test(@RequestHeader String headers){
        HashMap<String, Object> response = new HashMap<String, Object>();
        response.put("status", "201");
        System.out.println("headers :   "+headers);
        return response;
    }

    @PostMapping(path = "/LogIn")
    public ResponseEntity logIn(@Valid @RequestBody UserLoginRequestDTO user){
        try {
            User foundUser = services.logIn(user);
            HashMap<String, Object> response = new HashMap<String, Object>();
            String token = SharedFunctions.generateToken(foundUser);
            response.put("status", "200");
            response.put("userData", foundUser.toUserResponseDTO());
            response.put("token", token);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @GetMapping(path = "/details")
    public ResponseEntity createNewUser(@RequestHeader(required = false) String token){
        try {
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put("userData", services.getUserDetails(token).toUserResponseDTO());
            response.put("status", "302");
            return new ResponseEntity(response, HttpStatus.FOUND);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @PutMapping("/{userId}/cart/{productId}")
    public ResponseEntity<HashMap<String, Object>> addNewCartItem(
            @RequestHeader(required = false) String token,
            @PathVariable UUID userId,
            @PathVariable UUID productId){
        try{
            services.modifyingCartItems(true, token, userId, productId);
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put("status", "201");
            response.put("description", "Added new cart item successfully.");
            return new ResponseEntity(response, HttpStatus.CREATED);

        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @DeleteMapping("/{userId}/cart/{productId}")
    public ResponseEntity<HashMap<String, Object>> removeCartItem(
            @RequestHeader(required = false) String token,
            @PathVariable UUID userId,
            @PathVariable UUID productId){
        try{
            System.out.println("token"+token);
            services.modifyingCartItems(false, token, userId, productId);
            HashMap<String, Object> response = new HashMap<String, Object>();
            response.put("status", "201");
            response.put("description", "Removed cart item successfully.");
            return new ResponseEntity(response, HttpStatus.CREATED);

        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }
}
