package com.security.test.securityconftest.config.security;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JWTService {

    public String generateKey (){

        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            keyGenerator.init(2048, SecureRandom.getInstanceStrong());
            byte[] encoded = keyGenerator.generateKey().getEncoded();

//        StringBuffer sb = new StringBuffer();
            String key = Base64.getEncoder().encodeToString(encoded);
        return key;
        }catch (Exception e){

        }
        return null;
    }

    public String generateJWT(String subject, Long ttlMillsTime, String uuid){

        return Optional.of(new Date())
                .map(v -> Jwts.builder()
                        .setAudience(null)
                        .setSubject(subject)
                        .setIssuer("test security")
                        .setExpiration(new Date(System.currentTimeMillis() + ttlMillsTime))
                        .setNotBefore(v)
                        .setIssuedAt(v)
                        .setId(uuid)
                        .signWith(Keys.hmacShaKeyFor(generateKey().getBytes()), SignatureAlgorithm.HS512)
                        .compact()
                )
                .get();
    }

    public String retrieveSubject(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(generateKey().getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
