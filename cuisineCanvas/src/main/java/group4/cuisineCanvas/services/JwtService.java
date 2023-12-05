package group4.cuisineCanvas.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    static private final String keySecret = "yE0fRHKerlbu6PiUfqtS2NYYEcDDXaHxUDyJYmgvQV8JUhWe4gCn4JXv2SYc4Dkw";
    static private final int jwtExpiration = 1000 * 60 * 60; // ms

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(keySecret);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date()).setExpiration(new Date((new Date()).getTime() + jwtExpiration)).signWith(getSignInKey()).compact();
    }

    public String extractUserNameFromToken(String jwt) {
        String cleanToken = cleanToken(jwt);
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(cleanToken).getBody().getSubject();
    }

    public boolean isValidToken(String jwt, UserDetails userDetails) {
        try {
            String cleanToken = cleanToken(jwt);
            Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parse(cleanToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            e.getMessage();

        }
        return false;
    }

    private String cleanToken(String token) {
        if (token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }
}
