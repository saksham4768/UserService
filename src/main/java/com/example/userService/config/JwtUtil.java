package com.example.userService.config;

import com.example.userService.entity.Role;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil {
    private final String ENCODED_SECRET_KEY = "eW91cl9zZWNyZXR5b3VyX3NlY3JldHlvdXJfc2VjcmV0eW91cl9zZWNyZXQ=";
    private final byte[] SECRET_KEY = Base64.getDecoder().decode(ENCODED_SECRET_KEY);
    //header + payload + signature = JWT
    //signature = HMAC_SHA256(
    //    "<headerBase64>.<payloadBase64>",
    //    secretKey
    //)

    public String generateToken(String email, Set<Role> roles){
        return Jwts.builder() //	Starts building the JWT
                .setSubject(email)
                .claim("roles", roles) // 	Adds user identifier in the payload set in payload if want more than use .claims
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plusSeconds(3600)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //header and signature both create from here
                .compact();//	Returns the final JWT string
    }

    public String extractUsername(String token){
        System.out.println("🔍 Token passed for parsing: " + token);
        return Jwts.parserBuilder()//	Creates a JWT parser
                .setSigningKey(SECRET_KEY) // //// ✅ token is verified, parsed, claims extracted // If secret key is wrong, it should throw:io.jsonwebtoken.security.SignatureException
                .build()
                .parseClaimsJws(token)
                .getBody()//Gets the payload (claims) it returns claims object and from claims you get the subject in claims having key value
                .getSubject();//Gets the "sub" claim (usually username or email)
    }
}
