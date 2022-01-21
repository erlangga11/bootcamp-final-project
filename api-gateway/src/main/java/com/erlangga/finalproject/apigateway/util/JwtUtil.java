package com.erlangga.finalproject.apigateway.util;

import com.erlangga.finalproject.apigateway.exception.JwtTokenMalformedException;
import com.erlangga.finalproject.apigateway.exception.JwtTokenMissingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.Key;

@Component
public class JwtUtil {
//    @Value("${jwt.secret}")
    private String jwtSecret = "SecretKey Generator";
    byte[] keyData = jwtSecret.getBytes(Charset.forName("UTF-8"));
    private final Key key = new SecretKeySpec(keyData, SignatureAlgorithm.HS256.getJcaName());


    public Claims getClaims(final String token){
        try{
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
//            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){
            System.out.println(e.getMessage()+" => "+e);
        }
        return null;
    }

    public void validateToken(final String token) throws JwtTokenMalformedException, JwtTokenMissingException {
        try {
//            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
        }catch (SignatureException ex){
            System.out.println("Invalid signature"+ex.getMessage());
            throw new JwtTokenMalformedException("Invalid signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid token"+ex.getMessage());
            throw new JwtTokenMalformedException("Invalid token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired token"+ex.getMessage());
            throw new JwtTokenMalformedException("Expired token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported token"+ex.getMessage());
            throw new JwtTokenMalformedException("Unsupported token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT is empty"+ex.getMessage());
            throw new JwtTokenMissingException("JWT is empty");
        }
    }
}

