package com.example.abyad.AbyadExceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;

public class AbyadExceptions extends Throwable {

    @Autowired
    private AbyadErrorMapping abyadErrorMapping;

    private String errorKey;
    public AbyadExceptions(){
        this.errorKey = null;

    }

    public AbyadExceptions setErrorKey(String errorKey){
        this.errorKey = errorKey;
        return this;
    }

    public ResponseEntity<HashMap<String , Object>> getErrorMessage(){
        Error error = this.abyadErrorMapping.error().get(errorKey);
        if(error == null){
            error = new Error("UNKNOWN", "SOMETHING ERROR HAPPENED IN SERVER.", 500);
        }
        HashMap<String , Object> responseBody = new HashMap<String , Object>();
        responseBody.put("errorCode", error.errorKey());
        responseBody.put("errorDescription", error.description());
        responseBody.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<HashMap<String , Object>>(responseBody, HttpStatus.resolve(error.statusCode()));
    }
//
//    public ResponseEntity WrongEmailOrPassword(){
//        HashMap responseBody = new HashMap();
//        responseBody.put("timestamp", LocalDateTime.now());
//        responseBody.put("error", WrongEmailOrPassword);
//        return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
//    }
//
//
//    public ResponseEntity BadRequest(String message){
//        HashMap responseBody = new HashMap();
//        responseBody.put("timestamp", LocalDateTime.now());
//        responseBody.put("error", BadRequest +": " + message);
//        return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
//    }
//
//    public ResponseEntity UnAuthorized(){
//        HashMap responseBody = new HashMap();
//        responseBody.put("timestamp", LocalDateTime.now());
//        responseBody.put("error", UnAuthorized);
//        return new ResponseEntity(responseBody, HttpStatus.UNAUTHORIZED);
//    }
//
//    public ResponseEntity UserNotFound(){
//        HashMap responseBody = new HashMap();
//        responseBody.put("timestamp", LocalDateTime.now());
//        responseBody.put("error", UserNotFound);
//        return new ResponseEntity(responseBody, HttpStatus.NOT_FOUND);
//    }
//
//    public ResponseEntity UserAlreadyExists(){
//        HashMap responseBody = new HashMap();
//        responseBody.put("timestamp", LocalDateTime.now());
//        responseBody.put("error", UserAlreadyExists);
//        return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
//    }
}
