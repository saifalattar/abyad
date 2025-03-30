package com.example.abyad.Controller;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.Business.MagazinesServices;
import com.example.abyad.Schemas.Magazine;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1.0.0/magazines")
public class AbyadMagazinesController {
    final public MagazinesServices magazinesServices;

    public AbyadMagazinesController(MagazinesServices magazinesServices){
        this.magazinesServices = magazinesServices;
    }

    @GetMapping("/")
    public Object getAllMagazines(@RequestHeader(required = false) String token){
        try{
            HashMap<String , List<Magazine>> responseBody = new HashMap<>();
            responseBody.put("magazines", magazinesServices.getAllMagazines(token));
            return responseBody;
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @GetMapping("/{magazineName}")
    public ResponseEntity<HashMap<String , Object>> getAllMagazines(@RequestHeader(required = false) String token, @PathVariable("magazineName") String magazineName){
        try{
            HashMap<String,Object> responseBody = new HashMap<String,Object>();
            responseBody.put("magazines", magazinesServices.getMagazineByName(token, magazineName));
            return new ResponseEntity<HashMap<String , Object>>(responseBody, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @PostMapping("/")
    public ResponseEntity<HashMap<String , Object>> addNewMagazine(@RequestHeader(required = false) String token, @Valid @RequestBody(required = false) Magazine magazine){
        try{
            magazinesServices.addNewMagazine(token, magazine);
            HashMap<String,Object> responseBody = new HashMap<String,Object>();
            responseBody.put("status", "201");
            responseBody.put("magazine", magazine);
            responseBody.put("description", "Magazine added successfully");
            return new ResponseEntity<HashMap<String , Object>>(responseBody, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }

    @DeleteMapping("/{magazineID}")
    public ResponseEntity<HashMap<String , Object>> deleteMagazine(@RequestHeader(required = false) String token, @PathVariable UUID magazineID){
        try{
            magazinesServices.deleteMagazine(token, magazineID);
            HashMap<String,Object> responseBody = new HashMap<String,Object>();
            responseBody.put("status", "200");
            responseBody.put("description", "Magazine deleted successfully");
            return new ResponseEntity<HashMap<String , Object>>(responseBody, HttpStatus.OK);
        } catch (AbyadExceptions e) {
            return e.getErrorMessage();
        }
    }
    
}
