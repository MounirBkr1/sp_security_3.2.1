package com.mnr.sp_security3.service;

import com.mnr.sp_security3.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    //get it from searching on google: 256 bits secret key generator
    private final String SECRET_KEY="a1be45d75425ec04ac4e60178163a69a0c7f0cb49be28e64a884ecccf359bdd2";

    //extract username from the claims
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);

    }

    //validate token,return true if extractUsername = user.getUsername
    public boolean isValid(String token, UserDetails user){
        String username= extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }


    //extract a specified properties from token payload
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims =extractAllClaims(token);
        return resolver.apply(claims);
    }

    //extract claims from jwt token
    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getsigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //generate a token
    public String generateToken(User user){
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+24*60*1000))
                .signWith(getsigninKey())
                .compact();
        return token;

    }

    private SecretKey getsigninKey(){
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
