package com.goodapi.security;

import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author msaritas
 *
 */
public enum JwtUtil {

    INSTANCE;

    /**
     * Prefix for authentication header.
     */
    public final String AUTH_HEADER_NAME = "Bearer";

    /**
     * A Expiration period after that the JWT will be invalid.
     */
    private final Long sessionTokenExpTime = 24L;

    /**
     * A secret key to use JWT generation process
     */
    private final String secret = "goodapi.jwt.secret";

    /**
     * Generate a JWT with given userId and username. This information is stored in JWT.
     * 
     * @param userId
     * @param username
     * @param expireTime
     * @return A generated JWT
     */

    public String generateJWT(Long userId, String username, Long expireTime) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("userId", userId);

        if (expireTime == null) {
            expireTime = sessionTokenExpTime;
        }
        Long tokenTime = expireTime * 1000 * 60 * 60;

        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(new Date(System.currentTimeMillis() + tokenTime)).compact();

        return token;
    }

    public String generateJWT(Long userId, String username) {
        return generateJWT(userId, username, null);
    }

    /**
     * Parses given JWT and returns UserId.
     * 
     * @param token
     * @return
     */
    public Long parseToken(String token) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return Long.valueOf(body.get("userId").toString());
    }

    public String addAuthentication(Long userId, String username) {
        String token = generateJWT(userId, username);
        return token;
    }

}
