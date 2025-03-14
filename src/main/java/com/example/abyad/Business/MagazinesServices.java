package com.example.abyad.Business;

import com.example.abyad.AbyadExceptions.AbyadExceptions;
import com.example.abyad.AbyadExceptions.TypeOfException;
import com.example.abyad.Database.MagazineDatabase;
import com.example.abyad.Schemas.Magazine;
import com.example.abyad.Shared.SharedFunctions;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MagazinesServices {
    final private MagazineDatabase magazineDatabase;
    final private AbyadExceptions abyadExceptions;
    public MagazinesServices(MagazineDatabase magazineDatabase, @Qualifier("Exceptions") AbyadExceptions abyadExceptions){
        this.magazineDatabase = magazineDatabase;
        this.abyadExceptions = abyadExceptions;
    }

    public Magazine addNewMagazine(String token, Magazine magazine) throws AbyadExceptions{
        try{
            if (token == null) throw abyadExceptions.setErrorKey("162025");
            if(magazine == null) throw abyadExceptions.setErrorKey("152025");

            if (SharedFunctions.isTokenValid(token)){
                return magazineDatabase.save(magazine);
            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("BadRequest");
        }
    }

    public void deleteMagazine(String token, UUID magazineID) throws AbyadExceptions{
        try{
            if (token == null) throw abyadExceptions.setErrorKey("162025");

            if (SharedFunctions.isTokenValid(token)){
                magazineDatabase.deleteById(magazineID);
            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("BadRequest");
        }
    }

    public List<Magazine> getAllMagazines(String token) throws AbyadExceptions{
        try{
            if (token == null) throw abyadExceptions.setErrorKey("162025");

            if (SharedFunctions.isTokenValid(token)){
                return magazineDatabase.findAll();
            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("BadRequest");
        }
    }

    public List<Magazine> getMagazineByName(String token, String magazineName) throws AbyadExceptions{
        try{
            if (token == null) throw abyadExceptions.setErrorKey("162025");

            if (SharedFunctions.isTokenValid(token)){
                return magazineDatabase.findByNameLike("%"+magazineName+"%");
            }else{
                throw abyadExceptions.setErrorKey("UnAuthorized");
            }
        } catch (JwtException e) {
            throw abyadExceptions.setErrorKey("WrongToken");
        } catch (Exception e) {
            throw abyadExceptions.setErrorKey("BadRequest");
        }
    }
}
