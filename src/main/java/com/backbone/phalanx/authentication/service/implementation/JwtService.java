package com.backbone.phalanx.authentication.service.implementation;

import com.backbone.phalanx.authentication.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String jwtSigningKey;

    /**
     *
     * @param token
     * @return user's email
     */
    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     *
     * @param userDetails
     * @return generated token
     */
    public String generateToken(UserDetails userDetails, Date expiration) {
        Map<String, Object> claims = new HashMap<>();
        if (userDetails instanceof User) {
            claims.put("id", ((User) userDetails).getId());
            claims.put("email", ((User) userDetails).getEmail());
        }

        return generateToken(claims, userDetails, expiration);
    }

    /**
     *
     * @param token
     * @param userDetails
     * @return true if valid, false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        User User = (User) userDetails;
        return extractEmail(token).equals(User.getEmail()) && !isTokenExpired(token);
    }

    /**
     *
     * @param token
     * @return true if valid, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     *
     * @param token
     * @return expiration date of token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     *
     * @param claims
     * @param userDetails
     * @return generated token
     */
    private String generateToken(
            Map<String, Object> claims, UserDetails userDetails, Date expiration
    ) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     *
     * @param token
     * @param claimsResolvers
     * @return requested claim of token
     * @param <T>
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extratAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    /**
     *
     * @param token
     * @return all claims of token
     */
    private Claims extratAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     *
     * @return signing key
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
